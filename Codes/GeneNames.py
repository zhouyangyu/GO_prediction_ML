import numpy as np
import csv


dic = {}
with open ("geneNames.csv", 'rb') as csvfile:
	spamreader = csv.reader(csvfile)
	for row in spamreader:
		if row[0] == "":
			row[0] = row[1]
		dic[row[0]] = row[1]
result = []
unfound = []
counter = 0
with open ("AllSamples.csv", 'rb') as csvfile:
	spamreader = csv.reader(csvfile)
	for row in spamreader:
		if row[0] in dic:
			result.append([dic[row[0]], row[0]])
		else:
			unfound.append([row[0]])
		counter += 1
		print (counter)
print unfound
np.savetxt("AllSamplesMN1.csv", np.array(result), delimiter=",", fmt="%s")

