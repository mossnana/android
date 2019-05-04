// Register Screen

class MainActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        register_button_register.setOnClickListener {
            performRegister()
        }

        already_have_account_text_view.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        // Register Function
        private fun performRegister() {
            val email = email_edittext_register.text.toString()
            val password = password_edittext_register.text.toString()
            
            // Use this statement will not show error when email or password is emply
            if (email.isEmply() || password.isEmply()) return@setOnClickListener

            // Use this statement wiil show error when user doesn't input email or password
            if (email.isEmply() || password.isEmply()) {
                Toast.makeText(this, "Please Enter your Email and Password !!!", Toast.LENGTH_SHORT).show()
                return
            }

            // Firebase Authenthication to create a user with email and password
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener

                // else if successful
                // ${it.result.user.uid} is user id
                Log.d("Main", "Register Successful ${it.result.user.uid}")
            }
            .addOnFailureListener {
                Log.d("Main", "Failure to register ${it.message}")
            }
        }
    }
}


// Login Screen

class LoginActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_button_login.setOnClickListener {
            val email = email_edittext_login.text.toString()
            val password = password_edittext_login.text.toString()

            FirebaseAuth.getInstance.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                // Similar to Register Screen
            }
            .addOnFailureListener {

            }
        }

        back_to_register_textview.setOnClickListener {
            finish()
        }

    }
}