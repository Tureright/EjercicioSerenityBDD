package org.santi.abilities;

import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.thucydides.core.webdriver.ThucydidesWebDriverSupport;

public class NavegarLaWeb {
    public static BrowseTheWeb conEdge() {
        return BrowseTheWeb.with(
                ThucydidesWebDriverSupport.getDriver()
        );
    }

    public static BrowseTheWeb conChrome() {
        
        return BrowseTheWeb.with(
                ThucydidesWebDriverSupport.getDriver()
        );
    }
}
