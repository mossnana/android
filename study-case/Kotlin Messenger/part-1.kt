/*
Part 1
Object Reference and Intent Activity
 */


// Register Screen

class MainActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // setContentView(R.layout.{wanna show layout})
        setContentView(R.layout.activity_main)

        // click register
        register_button_register.setOnClickListener {

            // set id ช่อง Email ว่า email_edittext_register
            // จากนั้นสร้างตัวแปร email เพื่ออ้างอิงถึงช่อง email นั้น
            val email = email_edittext_register.text.toString()
            val password = password_edittext_register.text.toString()
        }

        // click already have account
        already_have_account_text_view.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            // start to call LoginActivity
            startActivity(intent)
        }

    }
}


// Login Screen

class LoginActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }
}

// add xml tag in Android Manifest
/*
    <activity android:name=".LoginActivity">

    </activity>
 */