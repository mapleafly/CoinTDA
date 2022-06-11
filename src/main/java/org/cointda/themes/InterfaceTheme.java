/*
 * Copyright 2020 lif.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.cointda.themes;

import com.dlsc.workbenchfx.Workbench;
import javafx.collections.ObservableList;
import org.cointda.enums.ThemeEnum;
import org.cointda.util.PrefsHelper;

import java.util.Objects;

/**
 * @author lif
 */
public class InterfaceTheme {

    private Workbench workbench;

    public InterfaceTheme(Workbench workbench) {
        this.workbench = workbench;
    }

    public void initNightMode() {
        String themeValue = PrefsHelper.getPreferencesValue(PrefsHelper.THEME, ThemeEnum.LIGHT.toString());
        ThemeEnum themeEnum = ThemeEnum.valueOf(themeValue);
        switch (themeEnum) {
            case NIGHT:
                setNightMode(true);
                break;
            case LIGHT:
            default:
                setNightMode(false);
                break;
        }
    }

    private void setNightMode(boolean on) {
        String customTheme = Objects.requireNonNull(InterfaceTheme.class.getResource("customTheme.css"))
            .toExternalForm();
        String darkTheme = Objects.requireNonNull(InterfaceTheme.class.getResource("darkTheme.css"))
            .toExternalForm();
        ObservableList<String> stylesheets = workbench.getStylesheets();
        if (on) {
            stylesheets.remove(customTheme);
            stylesheets.add(darkTheme);
        } else {
            stylesheets.remove(darkTheme);
            stylesheets.add(customTheme);
        }
    }
}
