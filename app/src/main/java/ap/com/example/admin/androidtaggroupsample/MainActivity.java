package ap.com.example.admin.androidtaggroupsample;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private String[] strings = {"dfjdkj", "dhrdhfodhfo", "h", "fgoidjf", "hdfuh", "ghh", "dfhdhdhfdhfhdhfhd", "dfud", "dfhd", "dfdoih"};
    private List<String> items = new ArrayList<>();
    private ArrayList<String> selectedPosition = new ArrayList<>();
    private CutomRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        items =  Arrays.asList(strings);
        RecyclerView recyclerView = findViewById(R.id.chip_cloud);
       /* GridAutofitLayoutManager gridLayoutManager = new GridAutofitLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);*/
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CutomRecyclerAdapter(items, selectedPosition);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, MotionEvent motionEvent) {
                multiselect(position);

            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

        }

    private void multiselect(int position) {
        if (selectedPosition.contains(items.get(position))) {
            selectedPosition.remove(items.get(position));
        } else {
            selectedPosition.add(items.get(position));
        }
        refreshAdapter();
    }

    private void refreshAdapter() {
        adapter.selectedPosition = selectedPosition;
        adapter.strings = items;
        adapter.notifyDataSetChanged();
    }

    public class CutomRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        public List<String> strings;
        public ArrayList<String> selectedPosition;
        private boolean isClicked;
        private MyHolder myHolder;

        public CutomRecyclerAdapter(List<String> strings, ArrayList<String> selectedPosition) {
            this.strings = strings;
            this.selectedPosition = selectedPosition;
        }


        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.chiplayout, null);
            return new MyHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            myHolder = (MyHolder) holder;
            myHolder.text.setText(strings.get(position));

            if (selectedPosition.contains(strings.get(position))) {
                myHolder.mainLayout.setBackground(getResources().getDrawable(R.drawable.shape_chip_selected_drawable));
            } else {
                myHolder.mainLayout.setBackground(getResources().getDrawable(R.drawable.shape_chip_normal_drawable));
            }

            myHolder.text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isClicked) {
                        myHolder.mainLayout.setBackground(getResources().getDrawable(R.drawable.shape_chip_selected_drawable));
                        isClicked = true;
                    } else {
                        myHolder.mainLayout.setBackground(getResources().getDrawable(R.drawable.shape_chip_normal_drawable));
                        isClicked = false;

                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return strings.size();
        }

        public class MyHolder extends RecyclerView.ViewHolder {
            private final TextView text;
            private final RelativeLayout mainLayout;


            public MyHolder(View itemView) {
                super(itemView);
                text = itemView.findViewById(R.id.chip_text);
                mainLayout=itemView.findViewById(R.id.chip_main_layout);

            }
        }


    }

}
