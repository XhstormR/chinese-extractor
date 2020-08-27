opencc.exe -c s2t.json -i characters_s.txt -o characters_t.txt

fd -x busybox sh -c "sort -u -o {}.sort {} && mv -f {}.sort {}"

busybox tr [:upper:] [:lower:] < in.txt > out.txt
