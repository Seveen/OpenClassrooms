package com.openclassrooms.entrevoisins.ui.neighbour_details;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.events.FavoriteNeighbourEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailNeighbourActivity extends AppCompatActivity {
	public static String EXTRA_NEIGHBOUR = "EXTRA_NEIGHBOUR";
	private Neighbour neighbour;
	private NeighbourApiService mApiService;

	@BindView(R.id.detail_avatar_imageView)
	ImageView mAvatar;
	@BindView(R.id.detail_toolbar_title)
	TextView mToolbarTitle;
	@BindView(R.id.detail_back_btn)
	ImageButton mBackButton;

	@BindView(R.id.detail_cardView_name_textView)
	TextView mNameTextView;

	@BindView(R.id.detail_floatingActionButton)
	FloatingActionButton mAddFavoriteButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_neighbour);
		ButterKnife.bind(this);
		mApiService = DI.getNeighbourApiService();

		Intent intent = getIntent();
		neighbour = intent.getParcelableExtra(EXTRA_NEIGHBOUR);

		Glide.with(this)
				.load(neighbour.getAvatarUrl())
				.into(mAvatar);

		mToolbarTitle.setText(neighbour.getName());
		mNameTextView.setText(neighbour.getName());

		mAddFavoriteButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				EventBus.getDefault().post(new FavoriteNeighbourEvent(neighbour));
				neighbour.setFavorite(!neighbour.getIsFavorite());
				refreshFavoriteButton();
			}
		});

		mBackButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});

		refreshFavoriteButton();
	}

	@Override
	public void onStart() {
		super.onStart();
		EventBus.getDefault().register(this);
	}

	@Override
	public void onStop() {
		super.onStop();
		EventBus.getDefault().unregister(this);
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onFavoriteNeighbour(FavoriteNeighbourEvent event) {
		mApiService.setNeighbourToFavorite(event.neighbour);
	}

	private void refreshFavoriteButton() {
		if (neighbour.getIsFavorite()) {
			mAddFavoriteButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_orange_24dp));
		} else {
			mAddFavoriteButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_border_orange_24dp));
		}
	}
}
