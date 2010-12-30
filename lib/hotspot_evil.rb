require 'java'

module HotSpotEvil
  class << self
    def detect_hotspot_bits
      java.lang.management.ManagementFactory.runtimeMXBean.vmName =~ /^Java HotSpot\(TM\) (\d+)-Bit (\w+) VM$/
      raise 'This library is only known to work with HotSpot VM' unless $1
      $1.to_i
    end
  end
  
  HOTSPOT_BITS = detect_hotspot_bits
end

$: << File.dirname(__FILE__)
require 'rubygems'
require 'bitescript'
include BiteScript

require 'hotspot_evil/common_macros'

MagicAccessorImpl = Java::SunReflect::MagicAccessorImpl

BiteScript.bytecode_version = JAVA1_6

