package org.drmx;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

public class CinemaViewer {


    public static final String SEANCES_URL = "http://cinemadelux.ru/seances/";

    public static Map<String, List<SeanceDTO>> parseSeances() {

        Map<String, List<SeanceDTO>> seances = new HashMap<>();
        try {
            Document document = Jsoup.connect(SEANCES_URL).get();
            Elements contentDivs = document.select("div.area_seanse");
            Iterator iterator = contentDivs.iterator();

            while(iterator.hasNext()) {
                SeanceDTO seanceDTO = new SeanceDTO();
                Element seance = (Element) iterator.next();
                seanceDTO.setTime(seance.select("div.time").text());
                seanceDTO.setName(seance.select("div.name").select("a").text());
                seanceDTO.setPrice(seance.select("div.price").text());
                if(seances.containsKey(seanceDTO.getName())) {
                    seances.get(seanceDTO.getName()).add(seanceDTO);
                } else {
                    List<SeanceDTO> seanceList = new ArrayList<>();
                    seanceList.add(seanceDTO);
                    seances.put(seanceDTO.getName(), seanceList);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            return null;
        }
        return seances;
    }

    public static String getSeances() {
        StringBuilder seancesStr = new StringBuilder("Сеансы на сегодня:\n");

        for(List<SeanceDTO> seanceDTOs: parseSeances().values()) {
            seancesStr.append(seanceDTOs.get(0).getName() + "\n");
            for(SeanceDTO seanceDTO : seanceDTOs) {
                seancesStr.append(" " + seanceDTO.getTime() + " - " + seanceDTO.getPrice() + " р.\n");
            }
        }
        return seancesStr.toString();
    }

}
