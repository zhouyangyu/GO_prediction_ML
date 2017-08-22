import numpy as np
import csv
import matplotlib.pyplot as plt
from sklearn.linear_model import LogisticRegression
from sklearn.ensemble import RandomForestClassifier
from sklearn import neural_network
from sklearn import preprocessing
from sklearn.ensemble import AdaBoostRegressor
from sklearn.ensemble import AdaBoostClassifier
from sklearn.ensemble import BaggingRegressor
from sklearn.ensemble import BaggingClassifier
from sklearn.preprocessing import PolynomialFeatures
from sklearn.multiclass import OneVsRestClassifier
from sklearn.neighbors import KNeighborsClassifier

from sklearn.metrics import log_loss
from sklearn import svm
import random

def main():

	#pca = PCA(n_components = 35,whiten=True)
	recreatedDataSet = True
	dataSet = []
	feat = []
	tar = []
	with open ("DataBaseReal.csv", 'rb') as csvfile:
		spamreader = csv.reader(csvfile)
		for row in spamreader:
			row = map(float, row)
			dataSet.append(row)
	random.shuffle(dataSet)
	for ele in dataSet:
		feat.append(ele[0:-1])
		tar.append(ele[-1])
	feat = preprocessing.scale(feat).tolist()
	np.savetxt("TrainSet.csv", np.array(dataSet[:len(feat) * 80 / 100]), delimiter=",", fmt="%s")
	np.savetxt("TestSet.csv", np.array(dataSet[len(feat) * 80 / 100:]), delimiter=",", fmt="%s")

	#feat = np.asarray(poly.fit_transform(feat))
	#feat = pca.fit_transform(np.array(feat))
	#log = RandomForestClassifier(n_estimators = 200)
	

	## Returns a tuple 
	def getSet(train):
		feature = []
		target = []
		lst = []
		if train:
			fileName = 'TrainSet.csv'
		else:
			fileName = "TestSet.csv"
		with open (fileName, 'rb') as csvfile:
			spamreader = csv.reader(csvfile)
			for row in spamreader:
				if train and row[-1] == 1:
					for j in range(4):
						lst.append(row)
				else:
					lst.append(row)
		random.shuffle(lst)
		for row in lst:
			feature.append(row[:-1])
			target.append(row[-1])
		return feature, target


	(trainF, trainT) = getSet(True)
	(testF, testT) = getSet(False)


	tss = []
	fss = []
	for z in range(1):

		#trainF = poly.fit_transform(trainF)
		#trainF = pca.fit_transform(trainF)
		#featTest = poly.fit_transform(feat[thres:])
		#featTest = pca.fit_transform(feat[thres:])
		#log = AdaBoostRegressor(svm.SVC(degree = 3, kernel = 'rbf'), n_estimators = 3)
		log = RandomForestClassifier()
		#log = AdaBoostClassifier()
		#log = KNeighborsClassifier()
		#log = neural_network.MLPClassifier(hidden_layer_sizes = [29,29], algorithm = 'l-bfgs')
		#log = LogisticRegression()
		log.fit(trainF, trainT)
		result = log.predict(testF)
		print log.score(testF, testT)
		tNcorrect = 0
		tPcorrect = 0
		fN = 0
		fP = 0
		ones = 0
		predOnes = 0
		wrongPred = []
		falseCorrect = 0
		for i in range(len(result)):
			if result[i] != 1 and testT[i] != 1:
				tNcorrect += 1
			if result[i] == 1 and testT[i] == 1:
				tPcorrect += 1
			if result[i] == 1 and not testT[i] == 1:
				fP += 1
			if result[i] != 1 and not testT[i] != 1:
				fN += 1
		print "Tp rate is %f, Fp rate is %f" %(float (tPcorrect)/(tPcorrect + fN), float(fP)/(fP + tNcorrect))
		tss.append(float (tPcorrect)/(tPcorrect + fN))
		fss.append(float(fP)/(fP + tNcorrect))
			# 	wrongPred.append(i + thres + 1)
			# if tar[thres:][i] == 1:
			# 	ones += 1
			# 	if result[i] == 1:
			# 		predOnes += 1
		# print float(correct)/len(result)
		print "Find %d pits out of %d" %(tPcorrect, tPcorrect + fN)
		# print "check row:" 
		# print wrongPred
		# print falseCorrect

		np.savetxt("tryPredict.csv", np.array(result), delimiter=",", fmt="%s")
		print "\n"


if __name__ == "__main__":
	main()

