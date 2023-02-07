package ru.iteco.fmhandroid.ui.steps;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static ru.iteco.fmhandroid.ui.data.Helper.withIndex;

import android.os.SystemClock;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.ScreenElements.CommonElements;
import ru.iteco.fmhandroid.ui.ScreenElements.ControlPanelScreen;
import ru.iteco.fmhandroid.ui.data.Helper;

public class ControlPanelSteps {
    ControlPanelScreen controlPanelScreen = new ControlPanelScreen();
    CommonSteps commonSteps = new CommonSteps();
    CommonElements commonElements = new CommonElements();

    public void isControlPanelScreen() {
        Allure.step("Проверка элементов Control panel");
        controlPanelScreen.controlPanelScreenName.check(matches(isDisplayed()));
        controlPanelScreen.sortBtn.check(matches(isDisplayed()));
        controlPanelScreen.newsFilterBtn.check(matches(isDisplayed()));
        controlPanelScreen.createNewsBtn.check(matches(isDisplayed()));
    }


    public void openNewsFilterScreen() {
        Allure.step("Открыть окно расширенного фильтра");
        controlPanelScreen.newsFilterBtn.perform(click());
    }

    public void clickCreateNewsBtn() {
        Allure.step("Кликнуть кнопку создания новости");
        controlPanelScreen.createNewsBtn.perform(click());
    }

    public void deleteNews(String newsTitle) {
        Allure.step("Удалить новость");
        controlPanelScreen.deleteNewsBtn(newsTitle).perform(click());

    }

    public void confirmDeleting() {
        Allure.step("Подтвердить удаление");
        commonElements.deleteDialog.check(matches(isDisplayed()));
        commonSteps.clickOkBtn();
    }


    public void clickEditNews(int position) {
        Allure.step("Клинкть кнопку редактирования новости");
        onView(withIndex(withId(R.id.edit_news_item_image_view), position)).perform(click());
        SystemClock.sleep(3000);
    }

    public void clickOnRandomlySelectedNewsItem(int position) {
        Allure.step("Кликнуть произвольную новость");
        controlPanelScreen.blockOfNews.perform(actionOnItemAtPosition(position, click()));
        SystemClock.sleep(3000);
    }


    public String getEditedNewsTitle(int position) {
        Allure.step("Получить отредактированный заголовок новости");
        return Helper.Text.getText(onView(withIndex(withId(R.id.news_item_title_text_view), position)));
    }

    public String getEditedNewsDescription(int position) {
        Allure.step("Получить отредактированное содержание новости");
        return Helper.Text.getText(onView(withIndex(withId(R.id.news_item_description_text_view), position)));
    }

    public void checkNotActiveNewsStatus() {
        Allure.step("Проверка статуса Not active");
        controlPanelScreen.newsStatusNotActive.check(matches(withText("Not active")));
    }

    public void checkActiveNewsStatus() {
        Allure.step("Проверка статуса Active");
        controlPanelScreen.newsStatusActive.check(matches(withText("Active")));
    }

}
