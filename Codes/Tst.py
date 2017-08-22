import tensorflow as tf
from tensorflow.examples.tutorials.mnist import input_data
mnist = input_data.read_data_sets("MNIST_data/", one_hot=True)

recreatedDataSet = True
feat = []
tar = []
with open ("DataBaseReal.csv", 'rb') as csvfile:
	spamreader = csv.reader(csvfile)
	for row in spamreader:
		row = map(float, row)
		feat.append(row[:-1])
		tar.append(row[-1])
feat = preprocessing.scale(feat).tolist()

#feat = np.asarray(poly.fit_transform(feat))
#feat = pca.fit_transform(np.array(feat))
#log = RandomForestClassifier(n_estimators = 200)
	
logss = []
tss = []
fss = []
log = ()
#log = AdaBoostClassifier(n_estimators = 100 * (z+1))
#log = OneVsRestClassifier(RandomForestClassifier())
thres = len(feat) * 80 / 100

print "reached"

train = []
if recreatedDataSet:
	for i in range(len(feat[:thres])):
		if tar[:thres][i] == 1:
			for j in range(4):
				train.append(feat[:thres][i] + [tar[:thres][i]])
		else:
			train.append(feat[:thres][i] + [tar[:thres][i]])
	recreatedDataSet = False
	np.savetxt("train.csv", np.array(train), delimiter=",", fmt="%s")
else:
	with open ("train.csv", 'rb') as csvfile:
		spamreader = csv.reader(csvfile)
		for row in spamreader:
			train.append(map(float, row))

print "Size of training set: %d" %(len(train))
print "Size of test set: %d" %(len(feat) - thres)

random.shuffle(train)
trainF = []
trainT = []
for ele in train:
	trainF.append(ele[:-1])
	trainT.append(ele[-1])
#trainF = poly.fit_transform(trainF)
#trainF = pca.fit_transform(trainF)
featTest = feat[thres:]

x = tf.placeholder(tf.float32, [None, 10])
W = tf.Variable(tf.zeros([10, 5]))
b = tf.Variable(tf.zeros([5]))
y = tf.nn.softmax(tf.matmul(x, W) + b)
y_ = tf.placeholder(tf.float32, [None, 5])
cross_entropy = tf.reduce_mean(-tf.reduce_sum(y_ * tf.log(y), reduction_indices=[1]))
train_step = tf.train.GradientDescentOptimizer(0.5).minimize(cross_entropy)
init = tf.initialize_all_variables()
sess = tf.Session()
sess.run(init)
for i in range(1000):
	batch_xs, batch_ys = trainT.next_batch(100)
	sess.run(train_step, feed_dict={x: batch_xs, y_: batch_ys})
correct_prediction = tf.equal(tf.argmax(y,1), tf.argmax(y_,1))
accuracy = tf.reduce_mean(tf.cast(correct_prediction, tf.float32))
print(sess.run(accuracy, feed_dict={x: mnist.test.images, y_: mnist.test.labels}))