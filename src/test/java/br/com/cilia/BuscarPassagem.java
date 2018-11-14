package br.com.cilia;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import org.openqa.selenium.support.ui.Select;


public class BuscarPassagem {

    private static WebDriver driver;
    
//   Inicializar o teste, configurando o driver e acessando o site.
    @BeforeClass
    public static void configurarTeste(){
        driver = new ChromeDriver();
        driver.get("https://www.phptravels.net/login");
    }
 
//    Fecha o navegador após o teste.
    @AfterClass
    public static void finalizarTeste(){
        driver.quit();
    }
    
     @Test
    public void testaBuscaDePassagem() throws InterruptedException {

    
//    Login no site.    
    WebElement element = driver.findElement(By.name("username"));
     
    element.sendKeys("user@phptravels.com");

    element = driver.findElement(By.name("password"));
 
    element.sendKeys("demouser");
  
    driver.findElement(By.cssSelector("button.btn.btn-action.btn-lg.btn-block.loginbtn")).click();
    
    WebDriverWait wait = new WebDriverWait(driver, 10);   
    wait.until(ExpectedConditions.urlMatches("https://www.phptravels.net/account/"));
    assertEquals(driver.getCurrentUrl(), "https://www.phptravels.net/account/");
    
//    Se o Login for feito com sucesso o teste clica em "Flights" para acessar a página de busca de voos;
    
    driver.findElement(By.xpath("/html/body/nav/div/div[2]/ul[1]/li[3]/a/span")).click();
    
//    Se o acesso à página de logins por feito com sucesso, o teste clica no campo
//    "Enter City or Airport" e insere o texto "GYN" para busca. 
    driver.findElement(By.xpath("//*[@id=\"s2id_location_from\"]/a")).click();
    element = driver.findElement(By.xpath("//*[@id=\"s2id_location_from\"]/a"));
    element.sendKeys("GYN");
    
//    Aqui o teste espera o texto "Searching" sumir e exibir os resultados. 
    wait.until(ExpectedConditions.invisibilityOfElementWithText(By.cssSelector("#select2-drop > ul"), "Searching..."));
    
//    Caso haja sucesso e o Aeroporto de Santa Genoveva (GYN) apareça, o teste irá clicar no resultado.
    wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector("div.select2-result-label"), "Santa Genoveva (GYN)"));
    driver.findElement(By.cssSelector("div.select2-result-label")).click();

//    Agora o teste irá fazer a mesma coisa que fez antes mas no campo "Enter City or Airport"
    driver.findElement(By.xpath("//*[@id=\"s2id_location_to\"]/a")).click();
    driver.findElement(By.xpath("//*[@id=\"s2id_location_to\"]/a")).sendKeys("GRU");
    wait.until(ExpectedConditions.invisibilityOfElementWithText(By.cssSelector("#select2-drop > ul"), "Searching..."));
    wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector("div.select2-result-label"), "Guarulhos Arpt (GRU)"));
    driver.findElement(By.cssSelector("div.select2-result-label")).click();
    
//    A seguir o teste insere a data posterior à data atual do computador para fazer a busca no campo "Depart"
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate localDate = LocalDate.now().plusDays(1);
    driver.findElement(By.xpath("//*[@id=\"flights\"]/form/div[3]/div/input")).click();
    driver.findElement(By.xpath("//*[@id=\"flights\"]/form/div[3]/div/input")).sendKeys(dtf.format(localDate));
    
//    A seguir o teste escolhe a classe "FIRST" para o voo. 
    driver.findElement(By.xpath("//*[@id=\"flights\"]/form/div[9]/div[3]/div/select")).click();
    Select drpCountry = new Select(driver.findElement(By.xpath("//*[@id=\"flights\"]/form/div[9]/div[3]/div/select")));
    drpCountry.selectByVisibleText("First");

//    Por fim, o teste executa a busca.
    driver.findElement(By.xpath("//*[@id=\"flights\"]/form/div[6]/button")).click();
    
    }

}