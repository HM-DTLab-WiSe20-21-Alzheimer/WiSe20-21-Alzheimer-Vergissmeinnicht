<template>
  <div id="app">
	<div v-if="authState !== 'signedin'">
		<amplify-authenticator username-alias="email">
			<amplify-sign-up slot="sign-up" username-alias="email" :form-fields.prop="formFields">
			</amplify-sign-up>
		</amplify-authenticator>
	</div>
	<div class="page" v-if="authState === 'signedin'">
		<NavBar />
		<Banner />		
		<router-view class="content" />
		<div class="footer">
			©2021
		</div>
	</div>
  </div>
</template>

<style lang="scss">
@import url('https://fonts.googleapis.com/css2?family=Chivo&display=swap');

* {
	font-family: 'Chivo', sans-serif;
	margin: 0;
	padding: 0;
	color: #edf0f1;
}

html {
  width: 100%;
  height: auto;  
  background-color: #1b1b1b;
  background-size: 100% auto;
}

a {
	text-decoration: none;
}

#app {
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;  
  color: #2c3e50;
}

.page {

}

.content {	
	border-color:red;
	margin-bottom:30px;
}

.footer {
	line-height:25px;
	text-align: center;
	position: fixed;
	bottom: 0;
	width: 100%;
	height: 30px;
	background-color: rgba(105,158,60,0.9);//#3b3b3b;
}
</style>

<script>
import { onAuthUIStateChange } from '@aws-amplify/ui-components';
import { Auth } from 'aws-amplify';
import NavBar from './components/NavBar.vue'
import Banner from './components/Banner.vue'

export default {
  name: 'Login',
  components: {
	NavBar,
	Banner
  },
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
  methods: {
	signOut: async function() {
		try {
			await Auth.signOut();
		} catch (error) {
			console.log('error signing out: ', error);
		}
	}
  },
  beforeDestroy() {
    return onAuthUIStateChange;
  }
}
</script>
