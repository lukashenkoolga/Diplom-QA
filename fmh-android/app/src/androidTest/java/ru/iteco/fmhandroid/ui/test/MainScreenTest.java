package ru.iteco.fmhandroid.ui.test;

import androidx.test.espresso.PerformException;
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
import ru.iteco.fmhandroid.ui.steps.ClaimsSteps;
import ru.iteco.fmhandroid.ui.steps.CommonSteps;
import ru.iteco.fmhandroid.ui.steps.CreateClaimSteps;
import ru.iteco.fmhandroid.ui.steps.MainScreenSteps;
import ru.iteco.fmhandroid.ui.steps.NewsSteps;
import ru.iteco.fmhandroid.ui.steps.OurMissionSteps;

@RunWith(AllureAndroidJUnit4.class)
public class MainScreenTest {
    AuthSteps authSteps = new AuthSteps();
    MainScreenSteps mainScreenSteps = new MainScreenSteps();
    ClaimsSteps claimsSteps = new ClaimsSteps();
    NewsSteps newsSteps = new NewsSteps();
    AboutUsSteps aboutUsSteps = new AboutUsSteps();
    OurMissionSteps ourMissionSteps = new OurMissionSteps();
    CreateClaimSteps createClaim = new CreateClaimSteps();
    CommonSteps commonSteps = new CommonSteps();


    @Rule
    public ActivityTestRule<AppActivity> activityTestRule =
            new ActivityTestRule<>(AppActivity.class);

    @Before
    public void logoutCheck() {
        try {
            mainScreenSteps.checkMainScreenLoaded();
        } catch (PerformException e) {
            authSteps.authWithValidData(Helper.authInfo());
            authSteps.clickSignInBtn();
        } finally {
            mainScreenSteps.checkMainScreenLoaded();
        }
    }

    @Test
    @DisplayName("Проверка элементов экрана")
    @Description("Проверка корректности отображения всех элементов экаран Main")
    public void shouldCheckMainScreenElements() {
        mainScreenSteps.isMainScreen();
    }

    @Test
    @DisplayName("Проверка списка вкладок кнопки меню")
    @Description("При нажатии на кнопку меню в выпадающем списке есть разделы Main, Claims, News, About")
    public void shouldCheckActionMenuScreenList() {
        mainScreenSteps.clickActionMenuBtn();
        mainScreenSteps.checkMenuList();
    }

    @Test
    @DisplayName("Переход по вкладкам с помощью кнопки меню")
    @Description("При нажатии на название экрана в выпадающем списке кнопки меню, пользователь переходит на соответствующую вкладку приложения")
    public void shouldCheckTransitionToScreensViaMenuBtn() {
        mainScreenSteps.goToClaimsScreen();
        claimsSteps.isClaimsScreen();
        mainScreenSteps.goToMainScreen();
        mainScreenSteps.isMainScreen();
        mainScreenSteps.goToNewsScreen();
        newsSteps.isNewsScreen();
        mainScreenSteps.goToAboutScreen();
        aboutUsSteps.isAboutUsScreen();
    }

    @Test
    @DisplayName("Переход на вкладку Love is all")
    @Description("При нажатии на кнопку в виде бабочки пользователь переходит на вкладку Love is all")
    public void shouldCheckTransitionToOurMissionScreen() {
        mainScreenSteps.clickOurMissionBtn();
        ourMissionSteps.isOurMissionScreen();
    }

    @Test
    @DisplayName("Выход из приложения")
    @Description("При нажатии на кнопку в виде человечка пользователь может выйти из приложения")
    public void shouldCheckLogOut() {
        mainScreenSteps.clickLogOutBtn();
        authSteps.isAuthScreen();
    }

    @Test
    @DisplayName("Перейти на вкладку Новости с помощью All News и вернутся обратно")
    @Description("При нажатии на вкладке основного экрана кнопки All News пользователь переходит на вкладку News и может вернуться на оснвоной экран")
    public void shouldCheckAllNewsBtn() {
        mainScreenSteps.clickAllNews();
        newsSteps.isNewsScreen();
        mainScreenSteps.goToMainScreen();
        mainScreenSteps.isMainScreen();
    }

    @Test
    @DisplayName("Перейти на вкладку Претензии с помощью All Claims и вернутся обратно")
    @Description("При нажатии на вкладке основного экрана кнопки All Claims пользователь переходит на вкладку Claims и может вернуться на оснвоной экран")
    public void shouldCheckAllClaimsBtn() {
        mainScreenSteps.clickAllClaims();
        claimsSteps.isClaimsScreen();
        mainScreenSteps.goToMainScreen();
        mainScreenSteps.isMainScreen();
    }

    @Test
    @DisplayName("Развернуть/свернуть блок новостей")
    @Description("При нажатии на блок новостей новости сворачиваются, при повтороном нажатии - разворачиваются")
    public void shouldShowOrHideNewsBlock() {
        mainScreenSteps.expandAllNews(); // свернуть новости
        mainScreenSteps.allNewsNotDisplayed();
        mainScreenSteps.expandAllNews();
        mainScreenSteps.allNewsDisplayed();
    }

    @Test
    @DisplayName("Развернуть/свернуть блок претензий")
    @Description("При нажатии на блок претензий претензии сворачиваются, при повторономы нажатии - разворачиваются")
    public void shouldShowOrHideClaimsBlock() {
        mainScreenSteps.expandAllClaims(); // свернуть заявки
        mainScreenSteps.allClaimsNotDisplayed();
        mainScreenSteps.expandAllClaims();
        mainScreenSteps.allClaimsDisplayed();
    }

    @Test
    @DisplayName("Перейти к созданию претензии и вернуться обратно на главный экран")
    @Description("При нажатии на кнопку + пользователь переходит на экран создания претензии. При нажатии Cancel возвращается обратно на основной экран")
    public void shouldCheckNewClaimBtn() {
        mainScreenSteps.clickNewClaimBtn();
        createClaim.isCreatingClaimScreen();
        commonSteps.clickCancel();
        commonSteps.clickOkBtn();
        mainScreenSteps.isMainScreen();
    }

}
