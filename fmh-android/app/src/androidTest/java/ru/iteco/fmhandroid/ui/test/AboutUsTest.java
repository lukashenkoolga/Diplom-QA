package ru.iteco.fmhandroid.ui.test;

import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Description;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.data.Helper;
import ru.iteco.fmhandroid.ui.steps.AboutUsSteps;
import ru.iteco.fmhandroid.ui.steps.AuthSteps;
import ru.iteco.fmhandroid.ui.steps.MainScreenSteps;

@RunWith(AllureAndroidJUnit4.class)
public class AboutUsTest {
    AuthSteps authSteps = new AuthSteps();
    AboutUsSteps aboutUsSteps = new AboutUsSteps();
    MainScreenSteps mainScreenSteps = new MainScreenSteps();

    @Rule
    public ActivityTestRule<AppActivity> activityTestRule =
            new ActivityTestRule<>(AppActivity.class);

    @Before
    public void logoutCheck() {
        try {
            mainScreenSteps.checkMainScreenLoaded();
        } catch (Exception e) {
            authSteps.authWithValidData(Helper.authInfo());
            authSteps.clickSignInBtn();
        } finally {
            mainScreenSteps.checkMainScreenLoaded();
            mainScreenSteps.goToAboutScreen();
        }
    }

    @Test
    @DisplayName("Проверка элементов экрана")
    @Description("Корректность отображения всех элементов экрана About us")
    public void shouldCheckAboutUsScreenElements() {
        aboutUsSteps.isAboutUsScreen();
    }

    @Test
    @DisplayName("Проверка кликабельности ссылок")
    public void shouldCheckLinksAreClickable() {
        aboutUsSteps.privacyPolicyLinkClickable();
        aboutUsSteps.termsLinkClickable();
    }

    @Test
    @DisplayName("Вернуться на предыдущий экран")
    @Description("При нажатии на стрелочку на верхней панели пользователь возвращается на предыдущий экран")
    public void shouldCheckGoBackToPreviousScreen() {
        aboutUsSteps.clickReturnBtn();
        mainScreenSteps.isMainScreen();
    }

}
