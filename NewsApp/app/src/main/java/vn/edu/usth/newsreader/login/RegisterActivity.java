package vn.edu.usth.newsreader.login;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import vn.edu.usth.newsreader.R;
import vn.edu.usth.newsreader.storage.Prefs;

public class RegisterActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        registerButton = findViewById(R.id.registerButton);

        registerButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            // Kiểm tra và đăng ký người dùng mới thông qua SharedPreferences

            if (!email.isEmpty() && !password.isEmpty()) {
                new Thread(() -> {
                    boolean ok = Prefs.register(getApplicationContext(), email, password);
                    runOnUiThread(() -> {
                        Toast.makeText(this, ok ? "Đăng ký thành công" : "Người dùng đã tồn tại", Toast.LENGTH_SHORT).show();
                        if (ok) finish();
                    });
                }).start();
            } else {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
