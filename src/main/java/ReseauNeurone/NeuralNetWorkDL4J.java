package ReseauNeurone;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.deeplearning4j.datasets.iterator.impl.ListDataSetIterator;
import org.slf4j.LoggerFactory;
import org.deeplearning4j.nn.api.Layer;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.cpu.nativecpu.NDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.learning.config.Nesterovs;
import org.nd4j.linalg.learning.config.Sgd;
import org.nd4j.linalg.lossfunctions.LossFunctions;

public class NeuralNetWorkDL4J {
	public static enum NET_CHOOSE{
		net,target_net,policy_net

	}
	Random rng;
	
	MultiLayerNetwork net,target_net,policy_net;



	int numInputs;
	int numOutputs;
	private static final String LogLocation="./log/serialisation/";

	private static final String FileNeuralNetworkSave=LogLocation+"neuralNetwork"; 

	private boolean test;

	public NeuralNetWorkDL4J(double learningRate, int seed, int numInputs, int numOutputs ){
		test=true;
		rng = new Random(seed);
		
		this.numInputs = numInputs;
		this.numOutputs = numOutputs;
		
        int nCouche1 = numInputs/2;
		int nCouche2 = nCouche1/2;
		int nCouche3 = nCouche2/2;
        
        net = new MultiLayerNetwork(new NeuralNetConfiguration.Builder()
                .seed(seed)
                .weightInit(WeightInit.XAVIER)
                .updater(new Adam(learningRate))
                //.updater(new Sgd(learningRate))
                .list()
                .layer(0, new DenseLayer.Builder().nIn(numInputs).nOut(nCouche1)
                        .activation(Activation.SIGMOID)
                        .build())
				
				.layer(1, new DenseLayer.Builder().nIn(nCouche1).nOut(nCouche2)
                .activation(Activation.SIGMOID)
                .build())

				.layer(2, new DenseLayer.Builder().nIn(nCouche2).nOut(nCouche3)
                .activation(Activation.SIGMOID)
                .build())

                .layer(3, new OutputLayer.Builder(LossFunctions.LossFunction.MSE)
                        .activation(Activation.IDENTITY)
                        .nIn(nCouche3).nOut(numOutputs).build())
                .build()
        );

		target_net=new MultiLayerNetwork(new NeuralNetConfiguration.Builder()
                .seed(seed)
                .weightInit(WeightInit.XAVIER)
                .updater(new Adam(learningRate))
                //.updater(new Sgd(learningRate))
                .list()
                .layer(0, new DenseLayer.Builder().nIn(numInputs).nOut(nCouche1)
                        .activation(Activation.SIGMOID)
                        .build())
				
				.layer(1, new DenseLayer.Builder().nIn(nCouche1).nOut(nCouche2)
                .activation(Activation.SIGMOID)
                .build())

				.layer(2, new DenseLayer.Builder().nIn(nCouche2).nOut(nCouche3)
                .activation(Activation.SIGMOID)
                .build())

                .layer(3, new OutputLayer.Builder(LossFunctions.LossFunction.MSE)
                        .activation(Activation.IDENTITY)
                        .nIn(nCouche3).nOut(numOutputs).build())
                .build()
        );

		policy_net=new MultiLayerNetwork(new NeuralNetConfiguration.Builder()
		.seed(seed)
		.weightInit(WeightInit.XAVIER)
		.updater(new Adam(learningRate))
		//.updater(new Sgd(learningRate))
		.list()
		.layer(0, new DenseLayer.Builder().nIn(numInputs).nOut(nCouche1)
				.activation(Activation.SIGMOID)
				.build())
		
		.layer(1, new DenseLayer.Builder().nIn(nCouche1).nOut(nCouche2)
		.activation(Activation.SIGMOID)
		.build())

		.layer(2, new DenseLayer.Builder().nIn(nCouche2).nOut(nCouche3)
		.activation(Activation.SIGMOID)
		.build())

		.layer(3, new OutputLayer.Builder(LossFunctions.LossFunction.MSE)
				.activation(Activation.IDENTITY)
				.nIn(nCouche3).nOut(numOutputs).build())
		.build()
		);
        

        this.policy_net.init();
		this.target_net.init();
		this.target_net.setListeners(new ScoreIterationListener(1));
		this.policy_net.setListeners(new ScoreIterationListener(1));
        net.init();
        net.setListeners(new ScoreIterationListener(1));
		cleanLog();
	


	}
	public NeuralNetWorkDL4J(double learningRate, int seed, int numInputs, int numOutputs ,String filserial){
		test=false;
		rng = new Random(seed);
		
		this.numInputs = numInputs;
		this.numOutputs = numOutputs;
		
        int nCouche1 = numInputs/2;
		int nCouche2 = nCouche1/2;
		int nCouche3 = nCouche2/2;
        
		net = new MultiLayerNetwork(new NeuralNetConfiguration.Builder()
                .seed(seed)
                .weightInit(WeightInit.XAVIER)
                .updater(new Adam(learningRate))
                //.updater(new Sgd(learningRate))
                .list()
                .layer(0, new DenseLayer.Builder().nIn(numInputs).nOut(nCouche1)
                        .activation(Activation.SIGMOID)
                        .build())
				
				.layer(1, new DenseLayer.Builder().nIn(nCouche1).nOut(nCouche2)
                .activation(Activation.SIGMOID)
                .build())

				.layer(2, new DenseLayer.Builder().nIn(nCouche2).nOut(nCouche3)
                .activation(Activation.SIGMOID)
                .build())

                .layer(3, new OutputLayer.Builder(LossFunctions.LossFunction.MSE)
                        .activation(Activation.IDENTITY)
                        .nIn(nCouche3).nOut(numOutputs).build())
                .build()
        );

		target_net=new MultiLayerNetwork(new NeuralNetConfiguration.Builder()
                .seed(seed)
                .weightInit(WeightInit.XAVIER)
                .updater(new Adam(learningRate))
                //.updater(new Sgd(learningRate))
                .list()
                .layer(0, new DenseLayer.Builder().nIn(numInputs).nOut(nCouche1)
                        .activation(Activation.SIGMOID)
                        .build())
				
				.layer(1, new DenseLayer.Builder().nIn(nCouche1).nOut(nCouche2)
                .activation(Activation.SIGMOID)
                .build())

				.layer(2, new DenseLayer.Builder().nIn(nCouche2).nOut(nCouche3)
                .activation(Activation.SIGMOID)
                .build())

                .layer(3, new OutputLayer.Builder(LossFunctions.LossFunction.MSE)
                        .activation(Activation.IDENTITY)
                        .nIn(nCouche3).nOut(numOutputs).build())
                .build()
        );

		policy_net=new MultiLayerNetwork(new NeuralNetConfiguration.Builder()
		.seed(seed)
		.weightInit(WeightInit.XAVIER)
		.updater(new Adam(learningRate))
		//.updater(new Sgd(learningRate))
		.list()
		.layer(0, new DenseLayer.Builder().nIn(numInputs).nOut(nCouche1)
				.activation(Activation.SIGMOID)
				.build())
		
		.layer(1, new DenseLayer.Builder().nIn(nCouche1).nOut(nCouche2)
		.activation(Activation.SIGMOID)
		.build())

		.layer(2, new DenseLayer.Builder().nIn(nCouche2).nOut(nCouche3)
		.activation(Activation.SIGMOID)
		.build())

		.layer(3, new OutputLayer.Builder(LossFunctions.LossFunction.MSE)
				.activation(Activation.IDENTITY)
				.nIn(nCouche3).nOut(numOutputs).build())
		.build()
		);
        
		this.net.init();
		this.policy_net.init();
		this.target_net.init();

        net.setListeners(new ScoreIterationListener(1));
		cleanLog();
		load(filserial, true,NET_CHOOSE.net);
		load(filserial, true,NET_CHOOSE.policy_net);
		load(filserial, true,NET_CHOOSE.target_net);
		

	}
	
	public void fit(ArrayList<TrainExample> trainExamples, int nEpochs, int batchSize)
	{
		
		
		double[][] input = new double[trainExamples.size()][this.numOutputs];	
		double[][] output = new double[trainExamples.size()][trainExamples.get(0).getX().length];
		
			
		/*System.out.println("trainExamples.size()");
		System.out.println(trainExamples.size());
		
		System.out.println("nEpochs " + nEpochs);
		*/
		
		for(int i = 0; i < trainExamples.size(); i++ ) {
			
			input[i] = trainExamples.get(i).getX();
			output[i] = trainExamples.get(i).getY();
		}
		
		INDArray inputNDArray = Nd4j.create(input);
		INDArray outPut = Nd4j.create(output);
		
		
		DataSet dataSet = new DataSet(inputNDArray, outPut);
        List<DataSet> listDs = dataSet.asList();
        Collections.shuffle(listDs,rng);
        
        DataSetIterator iterator = new ListDataSetIterator<>(listDs,batchSize);
        
        
        for( int i=0; i<nEpochs; i++ ){
        	System.out.println("epoch " + i);
            iterator.reset();
            policy_net.fit(iterator);
        }
        
        
	}
	
	public double[] predict(double[] features,NET_CHOOSE n) {
		
		INDArray input = Nd4j.create(features,1,this.numInputs);
	    INDArray out=null;
		switch(n)
		{
			case net:
			{
				out = net.output(input, false	);
			}break;
			case policy_net :
			{
				out = policy_net.output(input, false	);
			}break;

			case target_net :
			{
				out = target_net.output(input, false	);
			}break;
			default: {
				throw new UnsupportedOperationException("Not a good use of predict");
			}

		}

	    return  out.toDoubleVector();
	    
	}

	public void save(long iteration)
	{
		File f=new File(FileNeuralNetworkSave+"_"+iteration);
		f.delete();
		try {
			this.policy_net.save(f,true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void load(String filename,boolean apprentissage,NET_CHOOSE n)
	{
		File f=new File(filename);
		try {
			switch(n)
			{
				case net:
				{
					this.net.load(f,apprentissage);
				}break;
				case policy_net:
				{
					this.policy_net.load(f,apprentissage);
				}break;
				case target_net:
				{
					this.target_net.load(f,apprentissage);
				}break;
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void cleanLog()
	{
		File f=new File(LogLocation);
		try {
			FileUtils.cleanDirectory(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	public void updateTarget(double T)
	{
		

		for(int i=0;i<this.policy_net.getnLayers();++i)
		{
			INDArray policyNetDict=this.policy_net.getLayer(i).params(),targetNetDict=this.target_net.getLayer(i).params();
			for(int j=0;j<policyNetDict.length();++j)
			{				
				targetNetDict.putScalar(j,policyNetDict.getDouble(j)*T+targetNetDict.getDouble(j)*(1-T));
			}
		} 
	}
	
}
