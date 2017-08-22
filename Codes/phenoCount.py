import numpy as np
import csv
from sets import Set

se = Set()
phenoCount = {}
genePhono = {}
feature = []

q = Set()

##get after new DataBaseT.csv


topPh = ["WBPhenotype:0000050",'WBPhenotype:0000688','WBPhenotype:0000535', 'WBPhenotype:0000017',
'WBPhenotype:0000689', 'WBPhenotype:0000886', 'WBPhenotype:0000154', 'WBPhenotype:0001645','WBPhenotype:0000643',
'WBPhenotype:0000700',
'WBPhenotype:0000697',
'WBPhenotype:0000038',
'WBPhenotype:0000006',
'WBPhenotype:0000054',
'WBPhenotype:0000583',
'WBPhenotype:0000062',
'WBPhenotype:0001889',
'WBPhenotype:0000676',
'WBPhenotype:0000869',
'WBPhenotype:0000645',
'WBPhenotype:0000061']







with open ("DataBaseTT.csv", 'rb') as csvfile:
	spamreader = csv.reader(csvfile)
	thres = 0
	for row in spamreader:
		if thres > 1397:
			break
		if row[0][-1] in "abcdefghijklmn":
			row[0] = row[0][0:-1]
		if row[-1] == '1':
			se.add(row[0])
		feature.append(row[0])




with open ("pheno.csv", 'rb') as csvfile:
	spamreader = csv.reader(csvfile)
	for row in spamreader:
		if not row[0] in genePhono:
			genePhono[row[0]] = Set()
		genePhono[row[0]].add(row[1])

		if row[0] in se:
			if row[1] in phenoCount:
				phenoCount[row[1]] = phenoCount[row[1]] + 1
			else:
				phenoCount[row[1]] = 1


lst = []
for ele in phenoCount:
	lst.append([ele,phenoCount[ele]])

result = []
for ele in feature:
	r = []
	for p in topPh:
		if ele in genePhono and p in genePhono[ele]:
			r.append(1)
		else:
			r.append(0)
	result.append(r)

np.savetxt("PhenoCount1.csv", np.array(lst), delimiter=",", fmt="%s")
np.savetxt("PhenoCount.csv", np.array(result), delimiter=",", fmt="%s")
