<template>
	<div>
		<div v-if="authState !== 'signedin'">You are signed out.</div>
		<amplify-authenticator username-alias="email">
			<amplify-sign-up slot="sign-up" username-alias="email" :form-fields.prop="formFields">
			</amplify-sign-up>
		</amplify-authenticator>
	</div>
</template>

<script>
// @ is an alias to /src

//export default {
//  name: 'Home',
//  components: {
//  }
//}

import { onAuthUIStateChange } from '@aws-amplify/ui-components'

export default {
  name: 'Login',
  created() {
    onAuthUIStateChange((authState, authData) => {
      this.authState = authState;
      this.user = authData;
    })
  },
  data() {
    return {
        user: undefined,
        authState: undefined,
        formFields: [
            { type: "email" },
            { type: "password" }
        ]
    }
  },
  beforeDestroy() {
    return onAuthUIStateChange;
  }
}
</script>
