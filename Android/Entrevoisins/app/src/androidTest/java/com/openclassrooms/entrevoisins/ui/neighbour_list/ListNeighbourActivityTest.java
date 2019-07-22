package com.openclassrooms.entrevoisins.ui.neighbour_list;


import android.support.test.espresso.DataInteraction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.*;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Objects;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertEquals;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ListNeighbourActivityTest {

    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityTestRule = new ActivityTestRule<>(ListNeighbourActivity.class);

    @Test
    public void deleteNeighbourFromListTest() {
        RecyclerView recyclerView = mActivityTestRule.getActivity().findViewById(R.id.list_neighbours);
        int countBefore = Objects.requireNonNull(recyclerView.getAdapter()).getItemCount();

        onView(allOf(withId(R.id.item_list_delete_button),
				childAtPosition(childAtPosition(withId(R.id.list_neighbours), 0),2),
				isDisplayed()))
				.perform(click());

        onView(allOf(isDisplayed(), withId(R.id.list_neighbours)))
                .check(RecyclerViewItemCountAssertion.withItemCount(lessThan(countBefore)));
	}

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup)parent).getChildAt(position));
            }
        };
    }
}
