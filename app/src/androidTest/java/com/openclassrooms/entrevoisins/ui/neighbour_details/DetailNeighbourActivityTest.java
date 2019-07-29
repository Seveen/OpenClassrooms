package com.openclassrooms.entrevoisins.ui.neighbour_details;


import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class DetailNeighbourActivityTest {

	@Rule
	public ActivityTestRule<ListNeighbourActivity> mActivityTestRule = new ActivityTestRule<>(ListNeighbourActivity.class);

	@Test
	public void detailNeighbourActivityOpensTest() {
		onView(allOf(isDisplayed(), withId(R.id.list_neighbours))).perform(actionOnItemAtPosition(0, click()));
		onView(withId(R.id.detail_constraintLayout)).check(matches(isDisplayed()));
	}

	@Test
	public void detailNeighbourNameDisplayedTest() {
		onView(allOf(isDisplayed(), withId(R.id.list_neighbours))).perform(actionOnItemAtPosition(0, click()));
		onView(withId(R.id.detail_cardView_name_textView)).check(matches(not(withText(""))));
	}
}
