import sklearn
from sklearn import cross_validation
import numpy as np

X = np.array([[1, 2], [3, 4]])
y = np.array([1, 2])
loo = cross_validation.LeaveOneOut(3)

print(loo)
sklearn.cross_validation.LeaveOneOut(n=2)
for train_index, test_index in loo:
	print("TRAIN:", train_index, "TEST:", test_index)
	X_train, X_test = X[train_index], X[test_index]
	y_train, y_test = y[train_index], y[test_index]
	print(X_train, X_test, y_train, y_test)