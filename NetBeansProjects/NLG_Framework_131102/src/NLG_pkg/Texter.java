/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package NLG_pkg;

import Data_pkg.HealthParameters.Health_Parameter;
import Data_pkg.Mesurements.Measurement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import simplenlg.framework.DocumentElement;
import simplenlg.framework.NLGElement;
import simplenlg.framework.NLGFactory;
import simplenlg.lexicon.Lexicon;
import simplenlg.phrasespec.SPhraseSpec;
import simplenlg.realiser.english.Realiser;

/**
 *
 * @author hadi
 */
public class Texter {

    public Lexicon lexicon;
    public NLGFactory nlgFactory;
    public Realiser realiser;

    public String FillSections(Measurement curmeasurement) {

        lexicon = Lexicon.getDefaultLexicon();
        nlgFactory = new NLGFactory(lexicon);
        realiser = new Realiser(lexicon);

        String AllsectionsStr = "";

        AllsectionsStr += GlobalSectionStr(curmeasurement);

        AllsectionsStr += StatisticSectionStr(curmeasurement);

        AllsectionsStr += EventssectionStr(curmeasurement);


        return AllsectionsStr;
    }

    public String GlobalSectionStr(Measurement curmeasurement) {

        String sectionStr;

        // 1 section, 1 paragraph, 1 sentence
        DocumentElement section = nlgFactory.createSection();
        DocumentElement par1 = nlgFactory.createParagraph();

        //aval bayad jomle shavad, bad tabdil be element
        NLGElement glob_elem = curmeasurement.global_msg.FillSentence();

        par1.addComponent(glob_elem);

        section.addComponent(par1);

        sectionStr = realiser.realise(section).getRealisation();
        return sectionStr;

    }

    public String StatisticSectionStr(Measurement curmeasurement) {

        String sectionStr;

        // 1 section, 1 paragraph, hplist.size * stat_mess sentences

        DocumentElement section = nlgFactory.createSection();
        DocumentElement par1 = nlgFactory.createParagraph();

        NLGElement tempstat_element;

        for (int i = 0; i < curmeasurement.hplist.size(); i++) {

            for (int j = 0; j < curmeasurement.hplist.get(i).stat_msg.size(); j++) {

                tempstat_element = curmeasurement.hplist.get(i).stat_msg.get(j).FillSentence();
//                        
                par1.addComponent(tempstat_element);
            }
        }

        section.addComponent(par1);

//        
        sectionStr = realiser.realise(section).getRealisation();
        return sectionStr;

    }

    public String EventssectionStr(Measurement curmeasurement) {

        //each HP is obe section
        //each event_type (eg inctresh_event) is one paragraph.
        String finalsectionsstr = "";

        //for (Health_Parameter hp : curmeasurement.hplist) {
        for (int i = 0; i < 1 /*this.hplist.size()*/; i++) {

            String sectionStr = "";
            // 1 section, eventtype.size paragraph, inctresh_msglist.size (inctresh_event.seze) sentences
            DocumentElement section = nlgFactory.createSection();

//            //yek paragraph baraye INCtresh...
//            DocumentElement par1 = nlgFactory.createParagraph();
//            NLGElement tempincevent_element;
//            for (Message inc_msg : curmeasurement.hplist.get(i).inctresh_msglist) {
//                tempincevent_element = inc_msg.FillSentence();
//                par1.addComponent(tempincevent_element);
//            }
//            section.addComponent(par_rsfl);

            //yek paragraph baraye rise falls...
            DocumentElement par_rsfl = nlgFactory.createParagraph();
            NLGElement tempincevent_element;
            for (Message rsfl_msg : curmeasurement.hplist.get(i).segrisefall_msglist) {
                tempincevent_element = rsfl_msg.FillSentence();
                par_rsfl.addComponent(tempincevent_element);
            }

            section.addComponent(par_rsfl);

            sectionStr = realiser.realise(section).getRealisation();
            finalsectionsstr += " " + sectionStr;
        }

        return finalsectionsstr;
    }

    public String testfill() {

        lexicon = Lexicon.getDefaultLexicon();
        nlgFactory = new NLGFactory(lexicon);
        realiser = new Realiser(lexicon);


        NLGElement s1 = nlgFactory.createSentence("Hi Doctor!");
        return realiser.realiseSentence(s1);

    }
}