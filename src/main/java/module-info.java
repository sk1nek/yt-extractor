open module me.mjaroszewicz{
    requires jdk.incubator.httpclient;
    requires com.fasterxml.jackson.databind;
    exports me.mjaroszewicz.extractor;
    requires jsoup;
    requires rhino;
}