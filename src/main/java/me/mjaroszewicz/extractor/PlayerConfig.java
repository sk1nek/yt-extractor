package me.mjaroszewicz.extractor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayerConfig {

    private Args args;

    private String assets;

    public Args getPlayerArgs() {
        return args;
    }

    public void setPlayerArgs(Args args) {
        this.args = args;
    }

    public String getAssets() {
        return assets;
    }

    public void setAssets(String assets) {
        this.assets = assets;
    }
}
