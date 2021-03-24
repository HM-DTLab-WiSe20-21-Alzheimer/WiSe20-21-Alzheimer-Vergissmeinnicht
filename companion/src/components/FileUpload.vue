<template>
	<div class="form_wrapper">
		<p>Benenne Eure Erinnerung: *<p>
		
		<input type="text" v-model="name" />
		<br><br>
		
		
		<p>Wähle ein Datum: *</p>
		<input id="datePicker" type="date" />
		<br><br>
		
		<!-- Radio buttons -->		
		<p>Über welches Medium soll eure Erinnerung erzählt werden? *</p>
		<label class="radioContainer">
			<input type="radio" id="audio" name="filetypeGroup" v-model="mediaType" value="audio" @change="changeType" />
			<span class="radioCustom"></span> Audio
		</label>
		<!--
		<label class="radioContainer">
			<input type="radio" id="images" name="filetypeGroup" v-model="mediaType" value="images" @change="changeType" />
			<span class="radioCustom"></span> Bilder
		</label>
		-->
		<br><br>
		
		<!-- Upload button -->
		<p>Wähle die Datei die abgespielt werden soll: *</p>
		<br>		
		<label for="uploadButton" class="uploadCustom"><p id="uploadLabelText">Datei auswählen...</p>	
			<input id="uploadButton" type="file" accept="audio/*" v-on:input="getFile($event)"/>
		</label>				
		<div id="soundWrapper">
			<br>
			<audio id="sound" controls></audio>			
		</div>
		<br><br>
		
		<p>Füge deiner Erinnerung Tags zum späteren Filtern hinzu (Tags mit Komma separieren):<p>		
		<input type="text" v-model="tags" />
		<br><br><br>		

		<!-- Submit button -->
		<input type="submit" id="uploadButton" v-on:click="get($event)"/>
		<label for="submitButton" class="submitCustom"><p id="submitLabelText">Speichern</p>	
			<input id="submitButton" type="submit" v-on:click="submit()"/>
		</label>
		
		<!-- Error Msg-->
		<p id="createError" class="createError"></p>
	</div>
</template>

<style lang="scss">
//------------------------------ DISABLE DEFAULT VISUALS ------------------------------//
input[type="radio"],
input[type="file"],
input[type="submit"],
#soundWrapper,
.createError {
	display: none;
}
//------------------------------ ERROR MESSAGE ------------------------------//
.createError {
	padding-top: 20px;
	color: red;
}
//------------------------------ DATE PICKER OVERRIDE ------------------------------//
input[type="date"] {
	margin-top: 10px;
	border: 1px solid #3b3b3b;
	background-color: #1b1b1b;
}
//------------------------------ UPLOAD BUTTON OVERRIDE ------------------------------//
.uploadCustom {
	display: block;
	line-height:30px;
	text-align: center;
	background-color: #3b3b3b;
	border-radius: 5px;
	width: 200px;
	height: 30px;
	border: 1px solid #3b3b3b;	
	cursor: pointer;
}
.uploadCustom:hover {
	border: 1px solid #4b4b4b !important;
}
.uploadCustom:active {
	border: 1px solid #699E3C;;
	//background-color: #4b4b4b;
}
//------------------------------ SUBMIT BUTTON OVERRIDE ------------------------------//
.submitCustom {
	display: block;
	line-height:30px;
	text-align: center;
	background-color: #699E3C;
	border-radius: 5px;
	width: 100px;
	height: 30px;
	border: 1px solid #3b3b3b;	
	cursor: pointer;
}
.submitCustom:hover {
	border: 1px solid #4b4b4b;
	//background-color: #4b4b4b;
}
.submitCustom:active {
	opacity: 0.9;
	border: 1px solid #699E3C;
	//background-color: #4b4b4b;
}
//------------------------------ RADIO BUTTON OVERRIDE ------------------------------//
/* Radio Button Boundaries */
.radioContainer {
  margin-top: 10px;
  margin-left: 10px;
  display: block;
  position: relative;
  padding-left: 20px;
  cursor: pointer;
  width:0px; //don't know why but this correctly scales the boundary for clicking
}
/* Background unselected */
.radioCustom {
	position: absolute;
	top: 3px;
	left: 0;
	height: 12px;
	width: 12px;
	border: 1px solid #3b3b3b;
	border-radius: 50%;  
}
/* Hover background unselected*/
.radioContainer:hover .radioCustom {
	border: 1px solid #4b4b4b;
}
/* Radio Button Checked background */
.radioContainer input:checked ~ .radioCustom {
	border: 1px solid #699E3C;//#2baa2f;
	background-color: transparent;
}
.radioCustom:after {
  content: "";
  position: absolute;
  display: none;
}
.radioContainer input:checked ~ .radioCustom:after {
  display: block;
}
/* Radio Button Inside */
.radioContainer .radioCustom:after {
	top: 1px;
	left: 1px;
	width: 10px;
	height: 10px;
	border-radius: 50%;
	background: #699E3C;//#2baa2f; //#c3df01;
}
//------------------------------ TEXT INPUT OVERRIDE ------------------------------//
input[type="text"] {
	width: 300px;
	padding: 12px 20px;
	margin: 8px 0;
	box-sizing: border-box;
	background-color:transparent;
	border-style: solid;
	border-radius: 5px;
	border-color: #3b3b3b;
}
input[type=text]:hover {
	border-color: #4b4b4b;
}
input[type=text]:focus {
	caret-color: #4b4b4b;
	//background-color: #c6c6c6;
	border-color: #699E3C;
}
</style>

<script>
import { Storage } from 'aws-amplify'; 
import { Auth } from 'aws-amplify';
export default {
	name: "FileUpload",
	data: function() {
		return {
			name: "",
			tags: "",
			list: "",
			date: "",
			mediaType: "audio",
			file: null
		}
	},
	methods: {
		submit: function() {
			this.date = document.getElementById('datePicker').value;
			console.log(this.file);
			console.log(this.date);
			let messageElement = document.getElementById("createError");
			if (this.file !== null && this.date !== "" && this.name !== "") {
				messageElement.style.display = "none";
				//send file to s3
				Storage.put(this.file.name, this.file, {
					level: 'private',
					contentType: 'text/plain'
				})
				.then(this.store())
				//.then(result => console.log(result))
				.catch(err => console.log(err));
				
				//confirm creation
				messageElement.innerHTML = "Erfolreich abgespeichert!";
				messageElement.style.color = "green";
				messageElement.style.display = "block";
			} else {
				messageElement.innerHTML = "Bitte fülle alle mit * gekennzeichneten Felder aus!";
				messageElement.style.color = "red";
				messageElement.style.display = "block";
			}		
		},
		getFile: function(e){
			this.file = e.target.files[0];
			var sound = document.getElementById("sound");
			sound.src = URL.createObjectURL(this.file);
			document.getElementById("soundWrapper").style.display = "block";
			document.getElementById('uploadLabelText').innerHTML = this.file.name;
		},
		get: async function() {
			this.list = await Storage.list('', {
				level: 'private'
			})
			.then(result => console.log(result))
			.catch(err => console.log(err));
		},		
		store: async function() {
			let info = await Auth.currentUserInfo();
			let userObject = '"user" : {'
							+ '"userID" : "' + info.username + '",'
							+ '"identityID" : "' + info.id + '"'
							+ '}';
			let memoryObject = '"memory" : {'
							+ '"name" : "' + this.name + '",'
							+ '"date" : "' + this.date + '",'
							+ '"tags" : "' + this.tags + '",'
							+ '"file" : "' + this.file.name + '"'
							+ '}';
			let jsonObject = '{'+ userObject + ',' + memoryObject + '}';
			var request = new XMLHttpRequest();
			request.open("POST", "https://1lw5m8t8m7.execute-api.eu-central-1.amazonaws.com/default/Vergissmeinicht-upload");
			request.send(jsonObject);
		},
		changeType: function () {
			if (this.mediaType === "audio") {
				document.getElementById("uploadButton").accept = "audio/*";
			} else {
				document.getElementById("uploadButton").accept = "image/png";
			}
			
		}
	}
}
</script>