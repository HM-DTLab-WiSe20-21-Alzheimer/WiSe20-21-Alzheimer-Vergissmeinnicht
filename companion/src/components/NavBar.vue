<template>
	<div class="nav">
		<ul class="nav_links">
			<li><router-link to="/">Home</router-link></li>
			<!--<li> |  </li>-->
			<li><router-link to="/newmemory">Erinnerung erstellen</router-link></li>
			<!--<li> | </li>-->
			<li><router-link to="/about">About</router-link></li>
			<li><a class="cta" href="#" ><button @click="signOut()">Logout</button></a></li>
		</ul>
	</div>
</template>

<style lang="scss">
ul {
	text-align:right;
}
li, a, button {
	font-size: 16px;
	text-decoration: none;
}

button {
	color: #edf0f1;
}

.nav {
	height: 97px;
}

.nav_links {
	width: 100%;
	background-color: #1b1b1b;//#e4e4e4;//rgba(228,228,228,1); /*#3b3b3b;*/
	//justify-content: flex-end;
	//align-items: right;
	padding: 30px 30px;
	position: fixed;
	top: 0;
	right: 0;
	list-style: none;
	
	li {
		display: inline-block;
		padding: 0px 20px;
		
		a {
			transition: all 0.3s ease 0s;
		}
		a:hover {
			color: #699E3C;//#c3df01;
		}
	}
}

button {
	margin-left: 20px;
	padding: 9px 25px;
	background-color: rgba(188,53,53,1);
	border: none;
	border-radius: 50px;
	cursor: pointer;
	transition: all 0.3s ease 0s;	
}

button:hover {
	background-color: rgba(188,53,53,0.8);
}
</style>

<script>
import { Auth } from 'aws-amplify';

export default {
	name: "NavBar",
	data: function() {
		return {
			filename: "",
			signedURL: "http://localhost:8080",
			list: ""
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
  }
}
</script>