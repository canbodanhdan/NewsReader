package vn.edu.usth.newsreader.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import vn.edu.usth.newsreader.R;
import vn.edu.usth.newsreader.storage.Prefs;

public class SignupTabFragment extends Fragment {

    private EditText emailEditText, passwordEditText;
    private Button signup_button;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Gắn giao diện cho Fragment
        View view = inflater.inflate(R.layout.fragment_signup_tab, container, false);

        // Ánh xạ các thành phần UI
        emailEditText = view.findViewById(R.id.signup_email);
        passwordEditText = view.findViewById(R.id.signup_password);
        signup_button = view.findViewById(R.id.signup_button);

        // Thiết lập sự kiện cho nút đăng ký
        signup_button.setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if (!email.isEmpty() && !password.isEmpty()) {
                new Thread(() -> {
                    boolean ok = Prefs.register(requireContext(), email, password);
                    requireActivity().runOnUiThread(() -> Toast.makeText(requireContext(), ok ? "Registration successful" : "User already exists", Toast.LENGTH_SHORT).show());
                }).start();
            } else {
                Toast.makeText(requireContext(), "Please enter complete information", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
