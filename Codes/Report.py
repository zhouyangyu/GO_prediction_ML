print(__doc__)

import numpy as np
import matplotlib.pyplot as plt
import numpy as np
import csv
from sklearn.cross_validation import train_test_split
from sklearn.metrics import confusion_matrix
#decisionT.py
from sklearn.ensemble import BaggingClassifier
from sklearn.neighbors import KNeighborsClassifier
from sklearn.cross_validation import cross_val_score
from sklearn.ensemble import RandomForestClassifier
from sklearn.tree import DecisionTreeClassifier
from sklearn.ensemble import AdaBoostRegressor
from sklearn.ensemble import AdaBoostClassifier

from sklearn.ensemble import BaggingRegressor

from sklearn import linear_model
import numpy as np
import csv
from sklearn.decomposition import PCA
from sklearn.preprocessing import PolynomialFeatures
from sklearn.multiclass import OneVsRestClassifier
from sklearn.svm import LinearSVC
from sklearn import neural_network
from sklearn.svm import SVC
import random
from sklearn.metrics import log_loss
from sklearn import preprocessing
from sklearn.linear_model import LogisticRegression
from sklearn.metrics import recall_score
from sklearn.metrics import precision_score
from sklearn.cross_validation import LeaveOneOut

# import some data to play with
	#pca = PCA(n_components = 35,whiten=True)
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
			row = map(float, row)
			# if train and row[-1] == 1:
			# 	for j in range(4):
			# 		lst.append(row)
			# else:
			lst.append(row)
	random.shuffle(lst)
	for row in lst:
		feature.append(row[:-1])
		target.append([row[-1]])
	return feature, target


def plot_confusion_matrix(cm, title='Confusion matrix', cmap=plt.cm.Blues):
    plt.imshow(cm, interpolation='nearest', cmap=cmap)
    plt.title(title)
    plt.colorbar()
    tick_marks = np.arange(2)
    plt.xticks(tick_marks, ["No Pit", "Pit"])
    plt.yticks(tick_marks, ["No Pit", "Pit"])
    plt.tight_layout()
    plt.ylabel('True label')
    plt.xlabel('Predicted label')

(trainF, trainT) = getSet(True)
(testF, testT) = getSet(False)

#poly = PolynomialFeatures(2)
#trainF = poly.fit_transform(np.array(trainF))
#testF = poly.fit_transform(np.array(testF))
trainF = preprocessing.scale(trainF)
testF = preprocessing.scale(testF)

tss = []
fss = []

print trainF.shape



# Run classifier, using a model that is too regularized (C too low) to see
# the impact on the results

#log = BaggingClassifier(KNeighborsClassifier(n_neighbors = 7), n_estimators = 50)
#log = BaggingClassifier(KNeighborsClassifier(n_neighbors = 3, metric = "manhattan"))
#log = neural_network.MLPClassifier(hidden_layer_sizes = [29,29])
#log = neural_network.MLPClassifier(hidden_layer_sizes = [12], alpha = 0.01, algorithm = 'adam')
#log = AdaBoostRegressor(LogisticRegression(C = 1), n_estimators = 10* (z + 1)) 
#log = BaggingClassifier(SVC(degree = 3, kernel = 'rbf', C = 1), n_estimators = 40)
#log = AdaBoostRegressor(LogisticRegression(), n_estimators = 40)
#log = RandomForestClassifier(n_estimators = 500)
#log = AdaBoostClassifier(LogisticRegression(C = 1), n_estimators = 30)
#log = LogisticRegression(C = 1)
log = KNeighborsClassifier(n_neighbors = 21, metric = 'manhattan', weights = 'distance')
log.fit(trainF, trainT)
y_pred = log.predict(testF)
print recall_score(testT, y_pred)
print precision_score(testT, y_pred)
print log.score(testF, testT)

	# score = []
	# tp = 0.0
	# fp = 0.0
	# fn = 0.0
	# recall_score = []
	# precision_score = []
	# loo = LeaveOneOut(n=1398)
	# for train_index, test_index in loo:
	# 	X_train = trainF[train_index]
	# 	X_test = trainF[test_index]
	# 	y_train = np.transpose(trainT)[0][train_index]
	# 	y_test = np.transpose(trainT)[0][test_index]
	# 	log.fit(X_train, y_train)
	# 	y_pred = log.predict(X_test)
	# 	score.append(log.score(X_test, y_test))
	# 	if y_test[0] == 1 and y_pred[0] == 1:
	# 		tp += 1
	# 	elif y_test[0] == 1 and y_pred[0] == 0:
	# 		fn += 1
	# 	elif y_test[0] == 0 and y_pred[0] == 1:
	# 		fp += 1

	# 	#recall_score = recall_score(y_test, y_pred)
	# 	#precision_score = precision_score(y_test, y_pred)
	# print (np.mean(score), np.std(score))
	# print tp / (tp + fp)
	# print tp / (tp + fn)
	#print log.score(testF, testT)
	#print log.feature_importances_

#Compute confusion matrix
#Compute confusion matrix
cm = confusion_matrix(testT, y_pred)
np.set_printoptions(precision=2)
print('Confusion matrix, without normalization')
print(cm)
plt.figure()
plot_confusion_matrix(cm)

#Normalize the confusion matrix by row (i.e by the number of samples
#in each class)
cm_normalized = cm.astype('float') / cm.sum(axis=1)[:, np.newaxis]
print('Normalized confusion matrix')
print(cm_normalized)
plt.figure()
plot_confusion_matrix(cm_normalized, title='Confusion matrix for Adaboost Logistic Regression( components)' )

plt.show()

