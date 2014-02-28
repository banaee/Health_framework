/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package NLG_pkg;

import simplenlg.features.Feature;
import simplenlg.features.Tense;
import simplenlg.framework.NLGElement;
import simplenlg.framework.NLGFactory;
import simplenlg.framework.PhraseElement;
import simplenlg.lexicon.Lexicon;
import simplenlg.phrasespec.NPPhraseSpec;
import simplenlg.phrasespec.SPhraseSpec;
import simplenlg.phrasespec.VPPhraseSpec;
import simplenlg.realiser.english.Realiser;

/**
 *
 * @author hadi
 */
public class Message {

    public Lexicon lexicon;
    public NLGFactory nlgFactory;
    public Realiser realiser;

    public Message() {
        this.tense = null;
        this.complenet = null;
    }
//    public String fin_text;
    public String precomplement;
    public String subject_st;
    public String object_st;
    public String object_postmod_st;
    public String preverbmodifier_st;
    public String verb_st;
    public Tense tense;
    public String complenet;

    public NLGElement FillSentence() {

        lexicon = Lexicon.getDefaultLexicon();
        nlgFactory = new NLGFactory(lexicon);

        SPhraseSpec P = nlgFactory.createClause();

        //precomplement  baraye jomle ast na baraye Fael, hala felan
        //P.addPreModifier(precomplement);
        // in bayad behtar baashe, yani ye complenet new she, sar o samun dashte baashe        

        //subject
        NPPhraseSpec subj = nlgFactory.createNounPhrase(subject_st);
        subj.addPreModifier(precomplement);
        P.setSubject(subj);

        //object
        NPPhraseSpec obj = nlgFactory.createNounPhrase(object_st);
        if (object_postmod_st != null) {
            NLGElement obj_postmod = nlgFactory.createStringElement(object_postmod_st);
            obj.addPostModifier(obj_postmod);
        }
        P.setObject(obj);

        //verb
        VPPhraseSpec vrb = nlgFactory.createVerbPhrase(verb_st);
        P.setVerb(vrb);
        P.addPreModifier(preverbmodifier_st);
        if (tense != null) {
            P.setFeature(Feature.TENSE, tense);
        }

        //complenet
        if (complenet != null) {
            P.setComplement(complenet); //felan ??? badan bayad new comlement baashad :D
        }

        NLGElement PP = nlgFactory.createSentence(P);

        return PP;
    }
}
