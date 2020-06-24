package com.capstone.booking.common.converter;

import com.capstone.booking.entity.dto.LanguageChanger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.stereotype.Component;

@Component
public class XmlConverter {

    public String toXmlString(LanguageChanger multiLang) throws JsonProcessingException {
        XmlMapper xmlMapper = new XmlMapper();
        multiLang.setEnglish(multiLang.getEnglish());
        multiLang.setJapanese(multiLang.getJapanese());
        multiLang.setVietnamese(multiLang.getVietnamese());
        String xml = xmlMapper.writeValueAsString(multiLang);
        return xml;
    }

    public LanguageChanger toLanguageChanger(String xmlString) throws JsonProcessingException {
        XmlMapper xmlMapper = new XmlMapper();
        LanguageChanger value = xmlMapper.readValue(xmlString, LanguageChanger.class);
        return value;
    }

    public String getLangauge(String language, LanguageChanger languageChanger){
        String getStr = "";
        if(language.equals("vn")){
            getStr = languageChanger.getVietnamese();
        }
        if(language.equals("en")){
            getStr = languageChanger.getEnglish();
        }
        if(language.equals("jp")){
            getStr = languageChanger.getJapanese();
        }
        return getStr;
    }
}
