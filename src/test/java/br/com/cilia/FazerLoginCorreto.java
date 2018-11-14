package br.com.cilia;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FazerLoginCorreto {

    private static WebDriver driver;

//   Inicializar o teste, configurando o driver e acessando o site.
    @BeforeClass
    public static void configurarTeste() {
        driver = new ChromeDriver();
        driver.get("https://www.phptravels.net/login");
    }

//    Fecha o navegador após o teste.
    @AfterClass
    public static void finalizarTeste() {
        driver.quit();
    }

    @Test
    public void testaLoginCcorreto() throws InterruptedException {

//    Login no site, inserindo o e-mail no campo "E-mail".     
        WebElement element = driver.findElement(By.name("username"));
        element.sendKeys("user@phptravels.com");
        
//    Login no site inserindo a senha no campo "Password"
        element = driver.findElement(By.name("password"));
        element.sendKeys("demouser");

//     Clique no botão "LOGIN" e teste do redirecionamento para a paágina "My Account"   
        driver.findElement(By.cssSelector("button.btn.btn-action.btn-lg.btn-block.loginbtn")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.urlMatches("https://www.phptravels.net/account/"));
        assertEquals(driver.getCurrentUrl(), "https://www.phptravels.net/account/");

    }

}
