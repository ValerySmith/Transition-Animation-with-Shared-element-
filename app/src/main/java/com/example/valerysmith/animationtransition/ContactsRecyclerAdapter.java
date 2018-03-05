package com.example.valerysmith.animationtransition;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.valerysmith.animationtransition.databinding.RowContactBinding;
import java.util.List;

/**
 * Created by Valery Smith on 25.12.2017.
 */

public class ContactsRecyclerAdapter extends RecyclerView.Adapter<ContactsRecyclerAdapter.ContactsViewHolder> {
    private final Activity activity;
    private final List<Contacts> contactsList;

    public ContactsRecyclerAdapter(Activity activity, List<Contacts> contactsList) {
        this.activity = activity;
        this.contactsList = contactsList;
    }

    @Override
    public ContactsViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        RowContactBinding binding = RowContactBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ContactsViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(final ContactsViewHolder holder, final int position) {
        final Contacts contact = contactsList.get(holder.getAdapterPosition());
        holder.binding.setSample(contact);
        holder.binding.sampleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transitionToActivity(DetailActivity.class, holder, contact, R.string.transition_reveal1, R.string.transition_text);
            }
        });
    }

    private void transitionToActivity(Class target, ContactsViewHolder contactsViewHolder, Contacts contacts, int transitionIcon, int transitionText) {
        final Pair<View, String> [] pairs = TransitionHelper.createSafeTransitionParticipants(activity, false,
                new Pair<>(contactsViewHolder.binding.sampleIcon, activity.getString(transitionIcon)),
                new Pair<>(contactsViewHolder.binding.sampleName, activity.getString(transitionText)));
        startActivity(target, pairs, contacts);
    }

    private void startActivity(Class target, Pair<View, String>[] pairs, Contacts contacts) {
        Intent i = new Intent(activity, target);
        ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pairs);
        i.putExtra("contact", contacts);
        activity.startActivity(i, transitionActivityOptions.toBundle());
    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }

    public class ContactsViewHolder extends RecyclerView.ViewHolder {
        final RowContactBinding binding;

        public ContactsViewHolder(View rootView) {
            super(rootView);
            binding = DataBindingUtil.bind(rootView);
        }
    }
}


