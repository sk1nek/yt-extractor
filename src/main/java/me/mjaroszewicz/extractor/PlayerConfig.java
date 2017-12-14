package me.mjaroszewicz.extractor;

 class PlayerConfig {

    private Args args;

    private String assets;

    Args getPlayerArgs() {
        return args;
    }

    void setPlayerArgs(Args args) {
        this.args = args;
    }

    String getAssets() {
        return assets;
    }

    void setAssets(String assets) {
        this.assets = assets;
    }
}
