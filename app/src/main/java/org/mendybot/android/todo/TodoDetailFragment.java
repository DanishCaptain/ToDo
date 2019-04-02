package org.mendybot.android.todo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import org.mendybot.android.todo.ads.model.AdHandler;
import org.mendybot.android.todo.ads.model.AdsModel;
import org.mendybot.android.todo.model.TodoModel;
import org.mendybot.android.todo.model.domain.Todo;
import org.mendybot.android.todo.model.domain.TodoType;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import static org.mendybot.android.todo.R.id.item_todo_type_spin;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link TodoListActivity}
 * in two-pane mode (on tablets) or a {@link TodoDetailActivity}
 * on handsets.
 */
public class TodoDetailFragment
        extends Fragment
        implements CompoundButton.OnCheckedChangeListener, View.OnClickListener, AdapterView.OnItemSelectedListener {
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
//        AdsModel.getInstance().init(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);

        if (mItem != null) {
            EditText editTask = rootView.findViewById(R.id.item_task_edit);
            editTask.setText(mItem.getTask());
            editTask.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                    mItem.setTask(s.toString());
//                    TodoModel.getInstance().save(mItem);
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });

//            Spinner spinTaskType = rootView.findViewById(R.id.item_todo_type_spin);
//            spinTaskType.setAdapter(new ArrayAdapter<TodoType>(getContext(), R.layout.item_detail, TodoType.values()));
            Spinner spinTaskType =rootView.findViewById(R.id.item_todo_type_spin);
            spinTaskType.setAdapter(new ArrayAdapter<TodoType>(getContext(), android.R.layout.simple_spinner_item, TodoType.values()));
            spinTaskType.setSelection(mItem.getTodoType().ordinal());
            spinTaskType.setOnItemSelectedListener(this);




//            spinTaskType.setText(mItem.getTodoType().toString());
            /*
            spinTaskType.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                    mItem.setTodoType(TodoType.valueOf(s.toString()));
//                    TodoModel.getInstance().save(mItem);
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });
            */

            EditText editDetail = rootView.findViewById(R.id.item_detail_edit);
            editDetail.setText(mItem.getDetails());
            editDetail.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                    mItem.setDetails(s.toString());
//                    TodoModel.getInstance().save(mItem);
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });

            EditText editPriority = rootView.findViewById(R.id.item_priority);
            editPriority.setText(Integer.toString(mItem.getPriority()));
            editPriority.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                    int old = mItem.getPriority();
                    try {
                        mItem.setPriority(Integer.parseInt(s.toString()));
//                        TodoModel.getInstance().save(mItem);
                    } catch (Exception ex) {
                        mItem.setPriority(old);
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });

            CheckBox cb = rootView.findViewById(R.id.item_edit_completed);
            cb.setChecked(mItem.isCompleted());
            cb.setOnCheckedChangeListener(this);

            Button bSave = rootView.findViewById(R.id.save_item);
            bSave.setOnClickListener(this);
            Button bDelete = rootView.findViewById(R.id.delete_item);
            bDelete.setOnClickListener(this);


            final ImageView adView = rootView.findViewById(R.id.ad_2112_1);
            AdHandler handler = new AdHandler() {
                @Override
                public Activity getActivity() {
                    return TodoDetailFragment.this.getActivity();
                }

                @Override
                public ImageView getView() {
                    return adView;

                }
            };
            AdsModel.getInstance().add(handler);
        }

        return rootView;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        FragmentActivity a = getActivity();
        CheckBox cb1 = this.getActivity().findViewById(R.id.item_edit_completed);
        if (cb1 == buttonView) {
            mItem.setCompleted(isChecked);
        }
    }

    @Override
    public void onClick(View view) {
        FragmentActivity a = getActivity();
        if (view == a.findViewById(R.id.save_item)) {
            if (mItem != null) {
                TodoModel.getInstance().save(mItem);
            }
        } else if (view == a.findViewById(R.id.delete_item)) {
            if (mItem != null) {
                TodoModel.getInstance().deleteItem(mItem);
                TodoModel.getInstance().save();
                getActivity().navigateUpTo(new Intent(getActivity(), TodoListActivity.class));
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView == getActivity().findViewById(R.id.item_todo_type_spin)) {
            if (mItem != null) {
                mItem.setTodoType((TodoType) ((Spinner)adapterView).getSelectedItem());
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
