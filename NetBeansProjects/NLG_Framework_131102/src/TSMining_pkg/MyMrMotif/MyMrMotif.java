/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TSMining_pkg.MyMrMotif;

import Data_pkg.Mesurements.Measurement;
import ex.AlphabetTooLargeException;
import isax.TSUtils;
import isax.Word;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import motif.ETSDBInputType;
import motif.MotifCount;
import motif.MotifLocationManager;
import motif.MotifTree;

/**
 *
 * @author hadi
 */
public class MyMrMotif {

    //public String db = null;
    public MyMotifParameters params = null;

    public MyMotifParameters getParams() {
        return params;
    }
    public MyMotifCountmanager count = null;

    public MyMotifCountmanager getCount() {
        return count;
    }
    public MotifLocationManager locations = null;
    private MotifTree motifTree = null;

    public MotifTree getMotifTree() {
        return motifTree;
    }
    private MyStreamSummaries streamSummary = null;
    public MySpaceSavingSizes theSpaceSavingSizes = null;
    public File baseDir;
    public MyMemoryUtils memoryUtils = null;

    public MyMrMotif() {
        //this.db = System.getProperty("user.dir");
        this.params = new MyMotifParameters(this);
        this.count = new MyMotifCountmanager(this);
        this.locations = new MotifLocationManager();
        this.motifTree = new MotifTree();
        this.streamSummary = new MyStreamSummaries(this);
        this.theSpaceSavingSizes = new MySpaceSavingSizes(this);
        this.memoryUtils = new MyMemoryUtils(this);



    }

    public void findMotifs() throws AlphabetTooLargeException {


        File theDirectory = new File("/home/hadi/inputtext");

        if (theDirectory.isDirectory()) {

            baseDir = theDirectory;

            String[] fileListStr = theDirectory.list();

            double[] buf = null, tmp = null;

            double overlap = params.getOverLapPercentage();
            int totalsubseqs = 0;
            int overlapSize = (int) Math.round(params.getMotifLength()
                    * (overlap / 100.0));

            for (int it = 0; it < fileListStr.length; it++) {

                String fullPath = theDirectory + File.separator
                        + fileListStr[it];

                /* Motifs of the same size of each Time series in the Database */
                if (params.getInputType() == ETSDBInputType.FULL) {

                    // Initialize buffer.
                    if (it == 0) {
                        tmp = TSUtils.readTs(fullPath);
                        int size = tmp.length;
                        buf = new double[size];
                    }

                    TSUtils.readTs(fullPath, buf);

                    Word iSaxWord = new Word(buf, this.params.getWordLength(),
                            this.params.getInitialCardinality());

                    processWord(iSaxWord, buf, fileListStr[it]);

                    if (this.memoryUtils.isMemoryFull() && !params.isSpaceSaving()) {
                        System.out.println("ACTIVATING SPACE SAVING !!");
                        for (int k = getParams()
                                .getInitialCardinality(); k <= params
                                .getMaxCardinality(); k *= 2) {
                            this.theSpaceSavingSizes.put(k, count
                                    .get(k).size());
                        }

                        for (int k = getParams()
                                .getInitialCardinality(); k <= params
                                .getMaxCardinality(); k *= 2) {
                            System.out.println(count.get(k).size());
                        }
                        Toolkit.getDefaultToolkit().beep();
                        params.setSpaceSaving(true);
                    }

                } else if (params.getInputType() == ETSDBInputType.FULLONETSPERLINE) {
                    try {
                        BufferedReader br;

                        br = new BufferedReader(new FileReader(fullPath));

                        String line = "";
                        // Initialize buffers.
                        if (it == 0) {
                            tmp = new double[params.getMotifLength()];
                            buf = new double[params.getMotifLength()];
                        }

                        int j = 0;
                        while ((line = br.readLine()) != null) {

                            line = line.trim();

                            String[] allStrings = line.split(" +");

                            for (int i = 0; i < allStrings.length; i++) {
                                buf[i] = Double.parseDouble(allStrings[i]);
                            }

                            Word iSaxWord = new Word(buf, this.params
                                    .getWordLength(), this.params
                                    .getInitialCardinality());

                            processWord(iSaxWord, buf, fileListStr[it] + "-"
                                    + j);

                            j++;

                            if (this.memoryUtils.isMemoryFull() && !params.isSpaceSaving()) {
                                System.out
                                        .println("ACTIVATING SPACE SAVING !!");
                                for (int k = getParams()
                                        .getInitialCardinality(); k <= params
                                        .getMaxCardinality(); k *= 2) {
                                    this.theSpaceSavingSizes.put(k, count
                                            .get(k).size());
                                }

                                for (int k = getParams()
                                        .getInitialCardinality(); k <= params
                                        .getMaxCardinality(); k *= 2) {
                                    System.out.println(count.get(k).size());
                                }

                                Toolkit.getDefaultToolkit().beep();
                                params.setSpaceSaving(true);
                            }

                        }

                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } /* Sliding window approach */ else {
                    int size = params.getMotifLength();

                    if (it == 0) {
                        tmp = new double[size];
                        buf = new double[size];
                    }

                    try {

                        BufferedReader br = new BufferedReader(new FileReader(
                                fullPath));

                        String oneLine = "";
                        int i = 0;
                        int its = 0;
                        int currentI = 0;
                        int overlapK = 0;
                        int processes = 0;
                        while ((oneLine = br.readLine()) != null) {

                            oneLine = oneLine.trim();

                            if (oneLine.equals("")) {
                                break;
                            }

                            if (i < size || overlapK++ % overlapSize != 0) {
                                if (currentI == size) {
                                    currentI = 0;
                                }
                                buf[currentI] = Double.valueOf(oneLine);
                                i++;
                                currentI++;

                                continue;

                            }
                            its++;
                            if (currentI == size) {
                                currentI = 0;
                            }
                            if (currentI > size) {
                                System.err.println("I>size");
                                System.exit(1);
                            }
                            currentI = i % size;
                            buf[currentI] = new Double(oneLine);

                            TSUtils.arrayCopy(buf, tmp, currentI, size);

                            // WORK:
                            Word iSaxWord = new Word(tmp, this.params
                                    .getWordLength(), this.params
                                    .getInitialCardinality());

                            processWord(iSaxWord, tmp, fileListStr[it] + "-"
                                    + (overlapK - 1));

                            if (this.memoryUtils.isMemoryFull()
                                    && !params.isSpaceSaving()) {
                                System.out
                                        .println("ACTIVATING SPACE SAVING !!");
                                Toolkit.getDefaultToolkit().beep();

                                System.out.println("LOCATIONS SIZE:"
                                        + locations.size());
                                for (int k = getParams()
                                        .getInitialCardinality(); k <= params
                                        .getMaxCardinality(); k *= 2) {
                                    this.theSpaceSavingSizes.put(k, count
                                            .get(k).size());
                                }

                                for (int k = getParams()
                                        .getInitialCardinality(); k <= params
                                        .getMaxCardinality(); k *= 2) {
                                    System.out.println(count.get(k).size());
                                }

                                params.setSpaceSaving(true);
                            }

                            processes++;
                            // clean
                            iSaxWord = null;

                            // increments
                            i++;
                            // if (++currentI>size)currentI=0;
                            currentI++;

                        }
                        br.close();
                        totalsubseqs += processes;
                        System.out.println(this.memoryUtils
                                .getUsedMemoryPercentage()
                                + ";" + totalsubseqs);
                        // System.out.println(count.get(4).size() + "; " +
                        // locations.size() + "; " + motifTree.get(4).size());
                        // System.out.println(count.get(8).size() + "; " +
                        // locations.size() + "; " + motifTree.get(8).size());
                        // System.out.println(count.get(16).size() + "; " +
                        // locations.size() + "; " + motifTree.get(16).size());
                        // System.out.println(count.get(32).size() + "; " +
                        // locations.size() + "; " );
                        // TEMPORARY System.out.println("File:" +it +
                        // "; Subsequences="+processes+ "; "+fileListStr[it]);
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }

            }

        } else {
            System.err.println("Database should be a directory containing time series files.");
            System.exit(1);
        }

        /*
         * for (Integer card : count.keySet()) { System.out.println("Card: " +
         * card); for (Word saxW : count.get(card).keySet())
         * System.out.println(saxW + " " + count.get(card).get(saxW)); }
         */

        System.out.println("End exec.");

    }

    private void processWord(Word iSaxWord, double[] buf, String locationInfo)
            throws AlphabetTooLargeException {

        int currentCardinality = this.params.getInitialCardinality();


        Word parentWord = iSaxWord;
        while (currentCardinality <= params.getMaxCardinality()) {

            Word childWord = incrementWord(buf, currentCardinality,
                    locationInfo);

            if (currentCardinality > params.getInitialCardinality()) {
                this.motifTree.update(currentCardinality / 2, parentWord,
                        childWord);
            }

            currentCardinality *= 2;

            parentWord = childWord;

        }

    }

    private void incrementNotMonitored(Word saxWord, int currentCardinality) {

        // check if the list is full

        MotifCount motifCount = count.get(currentCardinality);

        // if (motifCount.size()>= params.getTopk()) {
        if (motifCount.size() >= theSpaceSavingSizes.get(currentCardinality)) {
            // update last and increment

            Word lw = streamSummary.get(currentCardinality).getLast();

            int lastCount = 0;
            if (lw != null) {
                /**
                 * Things to do when the list is full and you want to delete the
                 * last element of it.
                 */
                lastCount = motifCount.get(lw);
                motifCount.remove(lw);
                streamSummary.get(currentCardinality).get(lastCount).remove(lw);
                if (params.locationOn > 0) {

                    locations.deleteLocation(lw);

                }
                if (motifTree.get(currentCardinality) != null) {
                    if (motifTree.get(currentCardinality).containsKey(lw)) {
                        motifTree.get(currentCardinality).remove(lw);
                    } else {
                        System.err.println("current card =null");
                    }
                }

            } else {
                System.err.println("LW should not occur");
            }
            motifCount.put(saxWord, lastCount + 1);


        } else { // not full. simply insert and update last.
            count.incCount(currentCardinality, saxWord);

        }



    }

    private Word incrementWord(double[] series, int cardinality,
            String locationInfo) throws AlphabetTooLargeException {

        Word promotedWord = new Word(series, this.params.getWordLength(),
                cardinality);


        if (this.params.locationOn > 0) {
            this.locations.addLocation(promotedWord, locationInfo);
        }

        if (count.get(cardinality).containsKey(promotedWord)) {
            // increment count.
            this.count.incCount(cardinality, promotedWord);
        } else {
            if (!params.isSpaceSaving()) {
                this.count.incCount(cardinality, promotedWord);
            } else {
                incrementNotMonitored(promotedWord, cardinality);
            }
        }

        this.streamSummary.updateBucket(promotedWord, cardinality, this.count
                .get(cardinality).get(promotedWord));


        return promotedWord;
    }

    /*private void statisticalAnalysis() {

     MotifCount motifCount = this.getCount().get(
     this.getParams().getInitialCardinality());

     StatisticalAnalysis statisticalAnalysis = new StatisticalAnalysis();
     statisticalAnalysis.populate(motifCount, this.getParams()
     .getInitialCardinality(), this.getParams().getWordLength());

     }*/
//	public void setDb(String db) {
//		this.db = db;
//	}
    public void findMotifs(Measurement curMeasurement, double[] tsdata) {


        //File theDirectory = new File("/home/hadi/inputtext");

        //if (theDirectory.isDirectory()) {

        //baseDir = theDirectory;

        //String[] fileListStr = theDirectory.list();

        double[] buf = null, tmp = null;

        double overlap = params.getOverLapPercentage();
        int totalsubseqs = 0;
        int overlapSize = (int) Math.round(params.getMotifLength()
                * (overlap / 100.0));
        /*
         //for (int it = 0; it < fileListStr.length; it++) {

         //String fullPath = theDirectory + File.separator
         //		+ fileListStr[it];

         /* Motifs of the same size of each Time series in the Database 
         //				if (params.getInputType() == ETSDBInputType.FULL) {
         //
         //					// Initialize buffer.
         //					if (it == 0) {
         //						tmp = tsdata
         //						int size = tmp.length;
         //						buf = new double[size];
         //					}
         //
         //					TSUtils.readTs(fullPath, buf);
         //
         //					Word iSaxWord = new Word(buf, this.params.getWordLength(),
         //							this.params.getInitialCardinality());
         //
         //					processWord(iSaxWord, buf, fileListStr[it]);
         //
         //					if (this.memoryUtils.isMemoryFull()&& !params.isSpaceSaving()) {
         //						System.out.println("ACTIVATING SPACE SAVING !!");
         //						for (int k = getParams()
         //								.getInitialCardinality(); k <= params
         //								.getMaxCardinality(); k *= 2) {
         //							this.theSpaceSavingSizes.put(k, count
         //									.get(k).size());
         //						}
         //
         //						for (int k = getParams()
         //								.getInitialCardinality(); k <= params
         //								.getMaxCardinality(); k *= 2) {
         //							System.out.println(count.get(k).size());
         //						}
         //						Toolkit.getDefaultToolkit().beep();
         //						params.setSpaceSaving(true);
         //					}
         //
         //				} else if (params.getInputType() == ETSDBInputType.FULLONETSPERLINE) {
         //					try {
         //						BufferedReader br;
         //
         //						br = new BufferedReader(new FileReader(fullPath));
         //
         //						String line = "";
         //						// Initialize buffers.
         //						if (it == 0) {
         //							tmp = new double[params.getMotifLength()];
         //							buf = new double[params.getMotifLength()];
         //						}
         //
         //						int j = 0;
         //						while ((line = br.readLine()) != null) {
         //
         //							line = line.trim();
         //
         //							String[] allStrings = line.split(" +");
         //
         //							for (int i = 0; i < allStrings.length; i++) {
         //								buf[i] = Double.parseDouble(allStrings[i]);
         //							}
         //
         //							Word iSaxWord = new Word(buf, this.params
         //									.getWordLength(), this.params
         //									.getInitialCardinality());
         //
         //							processWord(iSaxWord, buf, fileListStr[it] + "-"
         //									+ j);
         //
         //							j++;
         //
         //							if (this.memoryUtils.isMemoryFull()&& !params.isSpaceSaving()) {
         //								System.out
         //										.println("ACTIVATING SPACE SAVING !!");
         //								for (int k = getParams()
         //										.getInitialCardinality(); k <= params
         //										.getMaxCardinality(); k *= 2) {
         //									this.theSpaceSavingSizes.put(k, count
         //											.get(k).size());
         //								}
         //
         //								for (int k = getParams()
         //										.getInitialCardinality(); k <= params
         //										.getMaxCardinality(); k *= 2) {
         //									System.out.println(count.get(k).size());
         //								}
         //
         //								Toolkit.getDefaultToolkit().beep();
         //								params.setSpaceSaving(true);
         //							}
         //
         //						}
         //
         //					} catch (Exception e) {
         //						// TODO Auto-generated catch block
         //						e.printStackTrace();
         //					}
         //				}

         /* Sliding window approach 
         //				else {
         */

        int size = params.getMotifLength();

        //if (it == 0) {
        tmp = new double[size];
        buf = new double[size];
        //}

//        try {

//						BufferedReader br = new BufferedReader(new FileReader(
//								fullPath));

        //String oneLine = "";
        int i = 0;
        int its = 0;
        int currentI = 0;
        int overlapK = 0;
        int processes = 0;
        //while ((oneLine = br.readLine()) != null) {
        for (double val : tsdata) {


//                oneLine = oneLine.trim();

//							if (oneLine.equals("")) {
//								break;
//							}

            if (i < size || overlapK++ % overlapSize != 0) {
                if (currentI == size) {
                    currentI = 0;
                }
                buf[currentI] = val; //Double.valueOf(oneLine);
                i++;
                currentI++;

                continue;

            }
            its++;
            if (currentI == size) {
                currentI = 0;
            }
            if (currentI > size) {
                System.err.println("I>size");
                System.exit(1);
            }
            currentI = i % size;
            buf[currentI] = new Double(val);

            TSUtils.arrayCopy(buf, tmp, currentI, size);

            // WORK:
            Word iSaxWord = null;
            try {
                iSaxWord = new Word(tmp, this.params
                        .getWordLength(), this.params
                        .getInitialCardinality());
            } catch (AlphabetTooLargeException ex) {
                Logger.getLogger(MyMrMotif.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                processWord(iSaxWord, tmp, "-" + (overlapK - 1));
            } catch (AlphabetTooLargeException ex) {
                Logger.getLogger(MyMrMotif.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (this.memoryUtils.isMemoryFull()
                    && !params.isSpaceSaving()) {
                System.out.println("ACTIVATING SPACE SAVING !!");
                Toolkit.getDefaultToolkit().beep();

                System.out.println("LOCATIONS SIZE:"
                        + locations.size());
                for (int k = getParams()
                        .getInitialCardinality(); k <= params
                        .getMaxCardinality(); k *= 2) {
                    this.theSpaceSavingSizes.put(k, count
                            .get(k).size());
                }

                for (int k = getParams()
                        .getInitialCardinality(); k <= params
                        .getMaxCardinality(); k *= 2) {
                    System.out.println(count.get(k).size());
                }

                params.setSpaceSaving(true);
            }

            processes++;
            // clean
            iSaxWord = null;

            // increments
            i++;
            // if (++currentI>size)currentI=0;
            currentI++;

        }
//            br.close();
        totalsubseqs += processes;
        System.out.println(this.memoryUtils
                .getUsedMemoryPercentage()
                + ";" + totalsubseqs);
        System.out.println(count.get(4).size() + "; "
                + locations.size() + "; " + motifTree.get(4).size());
        System.out.println(count.get(8).size() + "; "
                + locations.size() + "; " + motifTree.get(8).size());
        System.out.println(count.get(16).size() + "; "
                + locations.size() + "; " + motifTree.get(16).size());
        System.out.println(count.get(32).size() + "; "
                + locations.size() + "; ");
        // TEMPORARY System.out.println("File:" +it +
        // "; Subsequences="+processes+ "; "+fileListStr[it]);
//        } catch (FileNotFoundException e) {
//        } catch (IOException e) {
//        }
//
//				}
//
//			}

//		}
//		else {
//			System.err.println("Database should be a directory containing time series files.");
//			System.exit(1);
//		}

        /*
         * for (Integer card : count.keySet()) { System.out.println("Card: " +
         * card); for (Word saxW : count.get(card).keySet())
         * System.out.println(saxW + " " + count.get(card).get(saxW)); }
         */

        //System.out.println("End exec.");

    }
}
