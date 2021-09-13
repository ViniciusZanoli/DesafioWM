package br.com.webmotors.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Set;

public class BuscaHondaCityStep {
    WebDriver driver;
    WebDriverWait wait;
    String originalWindow = null;

    @Before
    public void setup(){
        System.setProperty("webdriver.chrome.driver", "src/main/resources/webDriver/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://www.webmotors.com.br/");
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 10);
        originalWindow = driver.getWindowHandle();
    }

    @Dado("que clique em comprar")
    public void que_clique_em_comprar() {
        WebElement btnComprar = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[text()='Comprar']")));
        btnComprar.click();
    }

    @E("depois em busca avançada")
    public void depois_em_busca_avançada() throws InterruptedException {
        WebElement btnBuscaAvancada = wait.until(ExpectedConditions.elementToBeClickable(By.id("navigationSearchAdvanced")));
        btnBuscaAvancada.click();

        Set<String> browserPages = driver.getWindowHandles();

        for (String child : browserPages){
            if(!child.equalsIgnoreCase(originalWindow)){
                driver.switchTo().window(child);
            }
        }

        Thread.sleep(2000);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='root']/div[3]/div[2]/button"))).click();
        Assert.assertEquals("Carros Novos e Usados em Todo o Brasil", driver.findElement(By.xpath("//h1[text()='Carros Novos e Usados em Todo o Brasil']")).getText());
    }

    @E("entao escolher a marca {string}")
    public void entao_escolher_a_marca(String marca) {
        WebElement nav = driver.findElement(By.cssSelector("div.NavBar"));
        Actions actions = new Actions(driver);
        actions.moveToElement(nav).perform();

        WebElement btnMarca = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//small[text()='honda']")));
        btnMarca.click();
    }

    @E("o modelo honda {string}")
    public void o_modelo_honda(String modelo) {
        WebElement btnModelos = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='Todos os modelos']")));
        btnModelos.click();

        WebElement btnOpcoes = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("CITY")));
        btnOpcoes.click();
    }

    @E("a versao")
    public void a_versao() {
        WebElement btnTodasAsVersoes = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='Todas as versões']")));
        btnTodasAsVersoes.click();

        WebElement btnVersao = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='root']/main/div[1]/div[2]/div/div[2]/div/div[4]/a[1]")));
        btnVersao.click();
    }

    @Entao("deve ser validado a mensagem Honda {string}")
    public void deve_ser_validado_a_mensagem_honda(String mensagem) throws InterruptedException {
        Thread.sleep(2000);
        Assert.assertEquals(mensagem, driver.findElement(By.xpath("//div[@class='Search-result Search-result--container-right']//h1[1]")).getText());
    }

    @After
    public void finish(){
        driver.quit();
    }
}
