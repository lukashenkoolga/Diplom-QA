package ru.iteco.fmhandroid.ui.steps;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static ru.iteco.fmhandroid.ui.data.Helper.elementWaiting;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.screenElements.CommonElements;
import ru.iteco.fmhandroid.ui.screenElements.CreateClaimsScreen;

public class CreateClaimSteps {
    CreateClaimsScreen createClaimsScreen = new CreateClaimsScreen();
    CommonElements commonElements = new CommonElements();


    public void createClaimScreenLoaded() {
        Allure.step("Загрузка страницы создания претензии");
        elementWaiting(withId(R.id.title_edit_text), 10000);
    }

    public void isCreatingClaimScreen() {
        Allure.step("Проверка элементов экрана Creating Claims");
        createClaimsScreen.creatingClaimsScreenName.check(matches(isDisplayed()));
        createClaimsScreen.titleField.check(matches(isDisplayed()));
        createClaimsScreen.executorField.check(matches(isDisplayed()));
        createClaimsScreen.claimDateField.check(matches(isDisplayed()));
        createClaimsScreen.claimTimeField.check(matches(isDisplayed()));
        createClaimsScreen.claimDescriptionField.check(matches(isDisplayed()));
        commonElements.saveBtn.check(matches(isDisplayed()));
    }

    public void fillInTitle(String title) {
        Allure.step("Заполнить поле заголовок");
        createClaimsScreen.titleField.perform(replaceText(title));
    }

    public void fillInExecutor(String executor) {
        Allure.step("Заполнить поле исполнитель");
        createClaimsScreen.executorField.perform(replaceText(executor));
    }

    public void fillInDate(String date) {
        Allure.step("Заполнить поле дата");
        createClaimsScreen.claimDateField.perform(replaceText(date));
    }

    public void fillInTime(String time) {
        Allure.step("Заполнить поле время");
        createClaimsScreen.claimTimeField.perform(replaceText(time));
    }

    public void fillItDescription(String description) {
        Allure.step("Заполнить поле описание");
        createClaimsScreen.claimDescriptionField.perform(replaceText(description));
    }

    public void clickTimeField() {
        Allure.step("Заполнить поле время");
        createClaimsScreen.claimTimeField.perform(click());
    }
}
