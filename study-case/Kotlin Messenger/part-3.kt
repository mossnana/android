/*
Part 3
Upload to Firebase Storage and Save User to Database
 */

// Register Screen
// First !!! Refactor MainActivity -> RegisterActivity
class RegisterActivity: AppCompatActivity() {
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
                
                uploadImageToFirebaseStorage()
            }
            .addOnFailureListener {
                Log.d("Main", "Failure to register ${it.message}")
            }
        }

        private fun uploadImageToFirebaseStorage() {
            if (selectedPhotoUri == null) return

            val filename = UUID.randomUUID().toString()
            FirebaseStorage.getInstance().getReference("./images/$filename")

            ref.putFile(selectedPhotoUri!!)
            .addOnSuccessListener {
                ref.downloadUrl.addOnSuccessListener {
                    saveUserToFirebaseDatabase(it.toString())
                }
            }
        }

        private fun saveUserToFirebaseDatabase(profileImageUrl: String) {
            val uid = Firebase.getInstance().uid ? : ""
            val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
            val user = User(uid, username_edittext_register.text.toString(), profileImageUrl)

            ref.setValue(user)
            .addOnSuccessListener {
                Log.d("RegisterActivity", "Finally We Uploaded Image to Firestore and get url to firebase database")
            }
        }

        selectphoto_button_register.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }
    }

    var selectedPhotoUri: Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            val selectedPhotoUri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)
            val bitmapDrawable = BitmapDrawable(bitmap)

            selectphoto_button_register.setBackgroundDrawable(bitmapDrawable)
        }
    }

}

// New Class For Upload Image
class User(val uid:String, val username: String, val profileImageUrl: String)