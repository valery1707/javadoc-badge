#!/bin/sh

version_new=${1}

version_cur=$(./gradlew properties | grep 'version:' | cut -d' ' -f2 | cut -d'-' -f1)

update_version() {
	echo "Update version from '${1}' to '${2}' in file ${3}"
    sed -i.bak "s_version '${1}.*_version '${2}'_g" ${3}
    rm ${3}.bak
    chmod 777 ${3}
}

if [ "x${version_new}" = "x" ] ; then
	version_1=$(echo -n "${version_cur}" | cut -d '.' -f1)
	version_2=$(echo -n "${version_cur}" | cut -d '.' -f2)
	version_3=$(echo -n "${version_cur}" | cut -d '.' -f3)
	version_new=${version_1}.${version_2}.$[${version_3} + 1]
fi

echo "Updating project version: ${version_cur} -> ${version_new}"
echo "New version is correct? (y/N)"
read x
if [ "$x" = "y" ] ; then
	echo "Continue"
elif [ "$x" = "Y" ] ; then
	echo "Continue"
else
	echo "Stop"
	exit 1
fi

update_version ${version_cur} ${version_cur} build.gradle
git add build.gradle
git commit --quiet -m "Release version ${version_cur}"
git tag -a -m "Release version ${version_cur}" v${version_cur}
git push openshift master

update_version ${version_cur} ${version_new}-SNAPSHOT build.gradle
git add build.gradle
git commit --quiet -m "Start working on snapshot version ${version_new}"

git push --quiet origin
git push --tags --quiet origin
