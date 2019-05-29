#FROM node:9.6.1 as builder
#COPY . angular
#
#RUN cd angular && npm install --no-optional
#RUN cd angular && npm prune
#RUN cd angular && npm run production
#

# Get latest nginx image
FROM nginx
# Copy build into nginx image
#COPY --from=builder ./angular/dist/KwetterFrontend/ /var/www
COPY ./dist/KwetterFrontend/ /var/www
# Copy nginx config file to default.conf
COPY ./nginx.conf /etc/nginx/conf.d/default.conf

COPY ./certs /certs
# Run server
# -g daemon off; is used so Docker can track the process properly,
# otherwise the container will stop immediately after starting
CMD ["nginx", "-c", "/etc/nginx/conf.d/default.conf",  "-g", "daemon off;"]
