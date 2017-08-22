import numpy as np
import csv

lst = []

with open ("pheno.csv", 'rb') as csvfile:
	spamreader = csv.reader(csvfile)
	for row in spamreader:
		if row[1] != "":
			lst.append(row[1])
		else:
			lst.append(row[0])

np.savetxt("phenoGeneName.csv", np.array(lst), delimiter=",", fmt="%s")
