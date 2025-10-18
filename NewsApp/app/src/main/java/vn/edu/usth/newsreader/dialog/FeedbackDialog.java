package vn.edu.usth.newsreader.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import vn.edu.usth.newsreader.R;

public class FeedbackDialog extends DialogFragment {

    private EditText feedbackEditText;
    private RatingBar ratingBar;
    private Button btnCancel, btnSend;

    public interface FeedbackListener {
        void onFeedbackSent(String feedback, int rating);
    }

    private FeedbackListener listener;

    public static FeedbackDialog newInstance() {
        return new FeedbackDialog();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FeedbackListener) {
            listener = (FeedbackListener) context;
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_feedback, container, false);
        
        initViews(view);
        setupListeners();
        
        return view;
    }

    private void initViews(View view) {
        feedbackEditText = view.findViewById(R.id.feedbackEditText);
        ratingBar = view.findViewById(R.id.ratingBar);
        btnCancel = view.findViewById(R.id.btnCancel);
        btnSend = view.findViewById(R.id.btnSend);
        
        // Configure input for better accented language support
        feedbackEditText.setImeOptions(android.view.inputmethod.EditorInfo.IME_ACTION_DONE);
        feedbackEditText.setInputType(android.text.InputType.TYPE_CLASS_TEXT | 
                                    android.text.InputType.TYPE_TEXT_FLAG_MULTI_LINE | 
                                    android.text.InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        
        // Ensure keyboard shows up correctly
        feedbackEditText.requestFocus();
        
        // Show keyboard after a short delay
        feedbackEditText.postDelayed(() -> {
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.showSoftInput(feedbackEditText, InputMethodManager.SHOW_IMPLICIT);
            }
        }, 100);
    }

    private void setupListeners() {
        btnCancel.setOnClickListener(v -> dismiss());

        btnSend.setOnClickListener(v -> {
            String feedback = feedbackEditText.getText().toString().trim();
            int rating = (int) ratingBar.getRating();

            if (feedback.isEmpty()) {
                Toast.makeText(getContext(), "Please enter your feedback", Toast.LENGTH_SHORT).show();
                return;
            }

            // Send email with feedback
            sendFeedbackEmail(feedback, rating);
            
            // Success notification
            Toast.makeText(getContext(), "Thank you for your feedback!", Toast.LENGTH_SHORT).show();
            
            // Invoke callback if present
            if (listener != null) {
                listener.onFeedbackSent(feedback, rating);
            }
            
            dismiss();
        });
    }

    private void sendFeedbackEmail(String feedback, int rating) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/plain; charset=UTF-8");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"feedback@newsapp.com"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback - NewsApp (Rating: " + rating + "/5 stars)");
        
        // Create a nicely formatted email body with UTF-8 encoding
        String emailBody = createEmailBody(feedback, rating);
        emailIntent.putExtra(Intent.EXTRA_TEXT, emailBody);
        
        try {
            startActivity(Intent.createChooser(emailIntent, "Send feedback via email"));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getContext(), "No email apps found", Toast.LENGTH_SHORT).show();
        }
    }

    private String createEmailBody(String feedback, int rating) {
        StringBuilder body = new StringBuilder();
        body.append("Hello NewsApp team,\n\n");
        body.append("I would like to share feedback about the app:\n\n");
        body.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        body.append("📱 RATING: ").append(rating).append("/5 ⭐\n");
        body.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n\n");
        body.append("💬 FEEDBACK:\n");
        body.append(feedback).append("\n\n");
        body.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        body.append("📧 This email was sent automatically from the NewsApp\n");
        body.append("🕒 Time: ").append(java.text.DateFormat.getDateTimeInstance().format(new java.util.Date())).append("\n");
        body.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n\n");
        body.append("Thank you for using NewsApp! ❤️");
        
        return body.toString();
    }
}
