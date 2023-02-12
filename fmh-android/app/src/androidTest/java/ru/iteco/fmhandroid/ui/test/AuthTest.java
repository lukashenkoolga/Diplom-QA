package ru.iteco.fmhandroid.ui.test;

import static ru.iteco.fmhandroid.ui.data.Helper.authInfo;

import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Description;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.steps.AuthSteps;
import ru.iteco.fmhandroid.ui.steps.MainScreenSteps;

@RunWith(AllureAndroidJUnit4.class)
public class AuthTest {

    AuthSteps authSteps = new AuthSteps();
    MainScreenSteps mainScreenSteps = new MainScreenSteps();

    @Rule
    public ActivityTestRule<AppActivity> activityTestRule =
            new ActivityTestRule<>(AppActivity.class);


    @Before
    public void logoutCheck() {
        try {
            authSteps.checkAuthPageLoaded();
            authSteps.isAuthScreen();
        } catch (Exception e) {
            mainScreenSteps.clickLogOutBtn();
            authSteps.checkAuthPageLoaded();
        }
    }


    @Test
    @DisplayName("Авторизация и выход ")
    @Description("Пользователь авторизуется с валидными данными и выходит из приложения с помощью кнопки Log out")
    public void shouldLogInAndLogOut() {
        authSteps.authWithValidData(authInfo());
        authSteps.clickSignInBtn();
        mainScreenSteps.checkMainScreenLoaded();
        mainScreenSteps.isMainScreen();
        mainScreenSteps.clickLogOutBtn();
        authSteps.isAuthScreen();
    }

    @Test
    @DisplayName("Проверка элементов экрана авторизации")
    @Description("Корректность отображения всех элементов экрана Авторизация")
    public void shouldCheckAuthScreenElements() {
        authSteps.isAuthScreen();
    }

    @Test
    @DisplayName("Вход с валидными данными")
    public void shouldLogInWithValidData() {
        authSteps.authWithValidData(authInfo());
        authSteps.clickSignInBtn();
        mainScreenSteps.checkMainScreenLoaded();
        mainScreenSteps.isMainScreen();
    }

}