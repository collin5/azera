package com.andela.hackathon.azera;

import com.google.firebase.auth.FirebaseAuth;


public class Logout {

	public void logout(){
		FirebaseAuth.getInstance().signOut();
	}

}
