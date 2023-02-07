package ru.iteco.fmhandroid.ui.steps;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.ui.ScreenElements.AuthScreenElements;
import ru.iteco.fmhandroid.ui.data.Helper;

public class AuthSteps {
    AuthScreenElements authScreenElements = new AuthScreenElements();

    public void isAuthScreen() {
        Allure.step("Проверка элементов экрана авторизации");
        authScreenElements.screenName.check(matches(isDisplayed()));
        authScreenElements.loginField.check(matches(isDisplayed()));
        authScreenElements.passField.check(matches(isDisplayed()));
        authScreenElements.signBtn.check(matches(isDisplayed()));
    }

    public void authWithValidData (Helper.AuthInfo info) {
        Allure.step("Авторизация с валидными данными");
        isAuthScreen();
        authScreenElements.loginField.perform(replaceText(info.getLogin()));
        authScreenElements.passField.perform(replaceText(info.getPass()));
    }

    public void clickSignInBtn() {
        Allure.step("Нажать кнопку Sign in");
        authScreenElements.signBtn.perform(click());
    }




}
