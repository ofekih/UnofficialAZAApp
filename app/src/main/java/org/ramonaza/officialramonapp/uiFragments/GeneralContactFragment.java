package org.ramonaza.officialramonapp.UIFragments;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.ramonaza.officialramonapp.R;
import org.ramonaza.officialramonapp.dataFiles.ContactInfoWrapper;

/**
 * Created by Ilan Scheinkman on 1/13/15.
 */
public class GeneralContactFragment extends Fragment{

    private static final String ARG_SECTION_NUMBER = "section_number";
    private ContactInfoWrapper aleph;

    public static GeneralContactFragment newInstance(int sectionNumber, ContactInfoWrapper aleph) {
        GeneralContactFragment fragment = new GeneralContactFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setAlpeh(aleph);
        fragment.setArguments(args);
        return fragment;
    }
    public void setAlpeh(ContactInfoWrapper aleph){
        this.aleph=aleph;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        ActionBar actionBar = getActivity().getActionBar();
        actionBar.setTitle(this.aleph.getName());
        View rootView=inflater.inflate(R.layout.fragment_contact_page,container,false);
        LinearLayout rootLayout=(LinearLayout) rootView.findViewById(R.id.cPageLayout);
        TextView information=new TextView(getActivity());
        information.setTextSize(22);
        String infoDump=String.format("Name:   %s\nGrade:   %s\nSchool:  %s\nAddress:   %s\nEmail:  %s\nPhone:   %s\n",aleph.getName(), aleph.getYear(), aleph.getSchool(),aleph.getAddress(),aleph.getEmail(),aleph.getPhoneNumber());
        information.setText(infoDump);
        rootLayout.addView(information);

        Button callButton=new Button(getActivity());
        callButton.setText("Call");
        callButton.setOnClickListener(new CallButtonListener().setAleph(this.aleph));
        rootLayout.addView(callButton);

        Button emailButton=new Button(getActivity());
        emailButton.setText("Email");
        emailButton.setOnClickListener(new EmailButtonListener().setAleph(this.aleph));
        rootLayout.addView(emailButton);

        Button addContactButton=new Button(getActivity());
        addContactButton.setText("Add to Contacts");
        addContactButton.setOnClickListener(new AddContactButtonListener().setAleph(this.aleph));
        rootLayout.addView(addContactButton);

        Button navButton=new Button(getActivity());
        navButton.setText("Get to house");
        navButton.setOnClickListener(new NavigatorButtonListener().setAleph(this.aleph));
        rootLayout.addView(navButton);

        return rootView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    public class CallButtonListener implements View.OnClickListener{
        ContactInfoWrapper contactInfoWrapperAleph;
        public CallButtonListener setAleph(ContactInfoWrapper inAleph){
            this.contactInfoWrapperAleph =inAleph;
            return this;
        }
        public void onClick(View v){
            try {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + contactInfoWrapperAleph.getPhoneNumber()));
                startActivity(callIntent);
            } catch (ActivityNotFoundException activityException) {
                Log.d("Calling Phone Number: "+ contactInfoWrapperAleph.getPhoneNumber(), "Call failed", activityException);
            }
        }
    }

    public class EmailButtonListener implements View.OnClickListener{
        ContactInfoWrapper contactInfoWrapperAleph;
        public EmailButtonListener setAleph(ContactInfoWrapper inAleph){
            this.contactInfoWrapperAleph =inAleph;
            return this;
        }
        public void onClick(View v){
            try {
                Intent emailIntent=new Intent(Intent.ACTION_SEND);
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL,new String[]{this.contactInfoWrapperAleph.getEmail()});
                startActivity(Intent.createChooser(emailIntent,"Email using:"));
            }catch (ActivityNotFoundException activityException) {
                Log.d("Emailing:: "+ contactInfoWrapperAleph.getEmail(), "Email failed", activityException);
            }
        }
    }

    public class AddContactButtonListener implements View.OnClickListener{
        ContactInfoWrapper contactInfoWrapperAleph;
        public AddContactButtonListener setAleph(ContactInfoWrapper inAleph){
            this.contactInfoWrapperAleph =inAleph;
            return this;
        }
        public void onClick(View v){
            try {
                Intent contactIntent=new Intent(ContactsContract.Intents.Insert.ACTION);
                contactIntent.setType(ContactsContract.Contacts.CONTENT_TYPE);
                contactIntent.putExtra(ContactsContract.Intents.Insert.NAME, contactInfoWrapperAleph.getName());
                contactIntent.putExtra(ContactsContract.Intents.Insert.PHONE, contactInfoWrapperAleph.getPhoneNumber());
                contactIntent.putExtra(ContactsContract.Intents.Insert.EMAIL, contactInfoWrapperAleph.getEmail());
                contactIntent.putExtra(ContactsContract.Intents.Insert.POSTAL, contactInfoWrapperAleph.getAddress());
                startActivity(contactIntent);
            }catch (ActivityNotFoundException activityException) {
                Log.d("Adding Contact: "+ contactInfoWrapperAleph.getName(), "Failed", activityException);
            }
        }
    }

    public class NavigatorButtonListener implements View.OnClickListener{
        ContactInfoWrapper contactInfoWrapperAleph;
        public NavigatorButtonListener setAleph(ContactInfoWrapper inAleph){
            this.contactInfoWrapperAleph =inAleph;
            return this;
        }
        public void onClick(View v){
            try {
                 String uri=String.format("google.navigation:q=%s", contactInfoWrapperAleph.getAddress().replace(" ","+"));
                 Intent navIntent=new Intent(Intent.ACTION_VIEW,Uri.parse(uri));
                startActivity(navIntent);
            }catch (ActivityNotFoundException activityException){
                Log.d("Directions to:"+ contactInfoWrapperAleph.getAddress(), "Failed", activityException);
            }
        }
    }

}