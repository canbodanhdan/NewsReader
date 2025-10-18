package vn.edu.usth.newsreader.quotes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import vn.edu.usth.newsreader.R;

import java.util.List;

// QuotesAdapter class is an adapter for RecyclerView, used to display a list of quotes
public class QuotesAdapter extends RecyclerView.Adapter<QuotesAdapter.QuoteViewHolder> {

    // List of quotes to display
    private List<Quote> quotes;

    // Constructor to initialize adapter with list of quotes
    public QuotesAdapter(List<Quote> quotes) {
        this.quotes = quotes;
    }

    @NonNull
    @Override
    // Create ViewHolder and assign item_quote layout for each item in the list
    public QuoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quote, parent, false);
        return new QuoteViewHolder(view);
    }

    @Override
    // Bind data from Quote object to Views in ViewHolder
    public void onBindViewHolder(@NonNull QuoteViewHolder holder, int position) {
        Quote quote = quotes.get(position);
        holder.quoteTextView.setText(quote.getQuote()); // Set quote content to TextView
        holder.authorTextView.setText("- " + quote.getAuthor()); // Set author name to TextView
    }

    @Override
    // Return the number of items in the quotes list
    public int getItemCount() {
        return quotes.size();
    }

    // ViewHolder class used to hold Views of an item in the list, helping to efficiently reuse Views
    public static class QuoteViewHolder extends RecyclerView.ViewHolder {
        TextView quoteTextView;
        TextView authorTextView;

        // Initialize ViewHolder and map Views in item_quote.xml
        public QuoteViewHolder(@NonNull View itemView) {
            super(itemView);
            quoteTextView = itemView.findViewById(R.id.quoteTextView); // TextView to display quote content
            authorTextView = itemView.findViewById(R.id.authorTextView); // TextView to display author name
        }
    }

    // This method updates the quotes list and notifies RecyclerView to refresh data
    public void setQuotes(List<Quote> quotes) {
        this.quotes = quotes;
        notifyDataSetChanged();
    }
}

