package ru.iteco.fmhandroid.ui.steps;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;
import static ru.iteco.fmhandroid.ui.data.Helper.elementWaiting;
import static ru.iteco.fmhandroid.ui.data.Helper.withIndex;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.screenElements.NewsScreen;
import ru.iteco.fmhandroid.ui.data.Helper;
import ru.iteco.fmhandroid.ui.data.Resources;

public class NewsSteps {

    NewsScreen newsScreen = new NewsScreen();
    Resources resources = new Resources();

    public void newsListLoaded() {
        Allure.step("Дождаться загрузки списка новостей");
        elementWaiting(withId(R.id.news_list_recycler_view), 10000);
    }

    public void isNewsScreen() {
        Allure.step("Проверка элементов экрана News");
        newsScreen.newsScreenName.check(matches(isDisplayed()));
        newsScreen.allNewsList.check(matches(isDisplayed()));
    }

    public void openFilter() {
        Allure.step("Открыть фильтр");
        newsScreen.filterNewsBtn.check(matches(isDisplayed()));
        newsScreen.filterNewsBtn.perform(click());
    }

    public void clickEditBtn() {
        Allure.step("Кликнуть кнопку редактирования");
        newsScreen.editButton.check(matches(isDisplayed()));
        newsScreen.editButton.perform(click());
    }

    public void openNews(int position) {
        Allure.step("Развернуть произвольно выбранную новость");
        newsScreen.expandNewsButton.perform(actionOnItemAtPosition(position, click()));
    }

    public void checkFirstNewsPublicationDate() {
        Allure.step("Проверка даты публикации первой новости");
        String firstNewsPublicationDate = Helper.Text.getText(onView(withIndex(withId(R.id.news_item_date_text_view), 0)));
        assertEquals(firstNewsPublicationDate, resources.newsPublicationDate);
    }

}
