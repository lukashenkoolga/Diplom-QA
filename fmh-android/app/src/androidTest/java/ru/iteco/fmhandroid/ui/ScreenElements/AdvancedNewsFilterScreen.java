package ru.iteco.fmhandroid.ui.ScreenElements;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.ViewInteraction;

import ru.iteco.fmhandroid.R;

public class AdvancedNewsFilterScreen {
    public ViewInteraction activeCheckBox = onView(withId(R.id.filter_news_active_material_check_box));
    public ViewInteraction notActiveCheckBox = onView(withId(R.id.filter_news_inactive_material_check_box));
}
