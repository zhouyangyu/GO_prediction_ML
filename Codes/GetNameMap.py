import numpy as np
import csv


lst = []
with open ("geneNames.csv", 'rb') as csvfile:
	spamreader = csv.reader(csvfile)
	for row in spamreader:
		if row[0] == "":
			row[0] = row[1]
		if row[1] == "":
			row[1] = '@'; 
		lst.append(row)


np.savetxt("nameMap.csv", np.array(lst), delimiter=",", fmt="%s")

