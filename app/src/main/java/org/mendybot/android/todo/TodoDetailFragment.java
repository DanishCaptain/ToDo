package org.mendybot.android.todo;

import android.app.Activity;
import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.EditText;

import org.mendybot.android.todo.model.TodoModel;
import org.mendybot.android.todo.model.domain.Todo;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link TodoListActivity}
 * in two-pane mode (on tablets) or a {@link TodoDetailActivity}
 * on handsets.
 */
public class TodoDetailFragment extends Fragment implements TextWatcher, CompoundButton.OnCheckedChangeListener {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The Todo content this fragment is presenting.
     */
    private Todo mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TodoDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle a = getArguments();
        if (a.containsKey(ARG_ITEM_ID)) {
            // Load the Todo content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            String key = getArguments().getString(ARG_ITEM_ID);
            mItem = TodoModel.getInstance().lookupItem(key);

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.getTask());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);

        if (mItem != null) {
            EditText edit = rootView.findViewById(R.id.item_detail_edit);
            edit.setText(mItem.getDetails());
            edit.addTextChangedListener(this);

            CheckBox cb = rootView.findViewById(R.id.item_edit_completed);
            cb.setChecked(mItem.isCompleted());
            cb.setOnCheckedChangeListener(this);
        }

        return rootView;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        mItem.setDetails(s.toString());
    }



    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        FragmentActivity a = getActivity();
        System.out.println(a);
        CheckBox cb1 = this.getActivity().findViewById(R.id.item_edit_completed);
        if (cb1 == buttonView) {
            mItem.setCompleted(isChecked);
//            this.getActivity().findViewById(R.id.item_list).invalidate();
//            this.getActivity().findViewById(R.id.item_list).setBackgroundColor(Color.RED);
        }

    }
}
