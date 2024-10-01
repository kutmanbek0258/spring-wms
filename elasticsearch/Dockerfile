FROM docker.elastic.co/elasticsearch/elasticsearch:7.16.3

ENV TZ=Asia/Bishkek

COPY /elasticsearch-analysis-morphology-7.16.3.zip /tmp/elasticsearch-analysis-morphology-7.16.3.zip
RUN bin/elasticsearch-plugin install file:/tmp/elasticsearch-analysis-morphology-7.16.3.zip
