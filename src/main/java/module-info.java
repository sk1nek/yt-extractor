open module me.mjaroszewicz{
    requires jdk.incubator.httpclient;
    requires com.fasterxml.jackson.databind;
    requires jackson.annotations;
    requires com.fasterxml.jackson.core;
    requires commons.lang;
    exports me.mjaroszewicz.extractor;
    requires jsoup;
    requires rhino;
}