package vergissmeinicht.helper;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.model.*;
import org.json.*;
import java.util.*;

public final class VergissmeinichtDynamoDB {
    private static final Random RANDOM = new Random();
    private static final String USER_TABLE = "vergissmeinicht-user";
    private static final String MEMORY_TABLE = "vergissmeinicht-memories";
    private static final String USER_ID = "userId";
    private static final String JSON_MEMORY = "memory";
    private static final String DATE_ATTRIBUTE = "date";
    private static final String TAGS_ATTRIBUTE = "tags";
    private static final String FILE_ATTRIBUTE = "file";
    private static final String NAME_ATTRIBUTE = "name";

    private VergissmeinichtDynamoDB() {}

    public static String getIdentityId(AmazonDynamoDB db, String userId) {
        Map<String, AttributeValue> key = new HashMap<>();
        key.put(USER_ID, new AttributeValue(userId));
        GetItemRequest getItemRequest = new GetItemRequest()
                .withKey(key)
                .withTableName(USER_TABLE);
        Map<String, AttributeValue> returnItem = db.getItem(getItemRequest).getItem();
        return returnItem.get("identityId").getS();
    }

    public static void storeUserIds(DynamoDB dynamoDb, String userId, String identityId) {        
        Item item = new Item()
                .withPrimaryKey(USER_ID, userId)
                .withString("identityId", identityId);
        dynamoDb.getTable(USER_TABLE).putItem(item);
    }

    public static void storeMemory(DynamoDB dynamoDb, JSONObject contents) {
        Item item = new Item()
                .withPrimaryKey(
                        USER_ID, contents.getJSONObject("user").getString("userID"),
                        "memoryId", getNextMemoryId(dynamoDb, contents.getJSONObject("user").getString("userID")))
                .withString(DATE_ATTRIBUTE, contents.getJSONObject(JSON_MEMORY).getString(DATE_ATTRIBUTE))
                .withString(TAGS_ATTRIBUTE, contents.getJSONObject(JSON_MEMORY).getString(TAGS_ATTRIBUTE))
                .withString(FILE_ATTRIBUTE, contents.getJSONObject(JSON_MEMORY).getString(FILE_ATTRIBUTE))
                .withString(NAME_ATTRIBUTE, contents.getJSONObject(JSON_MEMORY).getString(NAME_ATTRIBUTE));
        dynamoDb.getTable(MEMORY_TABLE).putItem(item);
    }

    public static String getFilenameForRandomMemory(String userId, AmazonDynamoDB db) {
        DynamoDB dynamoDB = new DynamoDB(db);
        int size = getNextMemoryId(dynamoDB, userId);
        if (size == 0) return null;
        int memoryId = RANDOM.nextInt(size);
        Map<String, AttributeValue> key = new HashMap<>();
        key.put(USER_ID, new AttributeValue(userId));
        key.put("memoryId", new AttributeValue().withN(String.valueOf(memoryId)));
        GetItemRequest getItemRequest = new GetItemRequest()
                .withKey(key)
                .withTableName(MEMORY_TABLE);
        Map<String, AttributeValue> returnItem = db.getItem(getItemRequest).getItem();
        return returnItem.get("file").getS();
    }

    public static String getFilenameForDate(String datum, String userId, AmazonDynamoDB db) {
        List<String> fileNames = new ArrayList<>();
        DynamoDB dynamoDB = new DynamoDB(db);
        Table table = dynamoDB.getTable(MEMORY_TABLE);
        ItemCollection<QueryOutcome> items = table.query(USER_ID, userId);

        for (Item item : items)
            if (item.getString("date").equals(datum)) fileNames.add(item.getString("file"));
        int results = fileNames.size();
        if (results > 0) {
            int index = RANDOM.nextInt(fileNames.size());
            return fileNames.get(index);
        }
        else return null;
    }

    public static int getNextMemoryId(DynamoDB db, String userId) {
        Table table = db.getTable(MEMORY_TABLE);
        ItemCollection<QueryOutcome> items = table.query(USER_ID, userId);
        //no size method for itemcollection?!
        int count = 0;
        for (Item item : items) count++;
        return count;
    }
}
